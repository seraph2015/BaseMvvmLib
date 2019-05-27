package org.seraph.lib.ui.comm.photopreview

import android.Manifest
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.request.FutureTarget
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.utlis.RxSchedulers
import org.seraph.lib.ui.base.ABaseSubscriber
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.utlis.LibUtils
import org.seraph.lib.view.CustomLoadingDialog
import java.io.File
import javax.inject.Inject

/**
 * 图片预览界面
 * date：2019/4/26 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
class PhotoPreviewVm @Inject constructor(
    application: Application,
    val act: PhotoPreviewActivity,
    var photoPreviewAdapter: PhotoPreviewAdapter
) : ABaseViewModel(application) {


    companion object {
        /**
         * 本地图片类型
         */
        const val IMAGE_TYPE_LOCAL = "image_type_local"

        /**
         * 图片列表数据
         */
        const val PHOTO_LIST = "photoList"
        /**
         * 当前选中的图片
         */
        const val CURRENT_POSITION = "currentPosition"
    }

    /**
     * 标题
     */
    val titleStr: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * 是否显示功能栏
     */
    val showBar: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    /**
     * 是否显示下载
     */
    val showDownload: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    /**
     * 是否显示查看原图
     */
    val showMaxImage: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    /**
     * 当前显示图片位置
     */
    private var currentPosition = 0

    private var rxPermissions: RxPermissions

    private var customLoadingDialog: CustomLoadingDialog

    init {
        //默认显示功能栏
        showBar.value = true

        rxPermissions = RxPermissions(act)

        customLoadingDialog = CustomLoadingDialog(act)
    }

    override fun start() {


    }


    /**
     * 切换bar显示
     */
    fun onSwitchUIShow() {
        showBar.value = !showBar.value!!
    }


    //更新显示当前位置的状态
    fun upDateCurrentPosition(position: Int) {
        currentPosition = position
        //是否是本地图片，本地图片不显示下载
        showDownload.value = photoPreviewAdapter.getItem(position).fromType != IMAGE_TYPE_LOCAL
        //标题
        titleStr.value = "${position + 1}/${photoPreviewAdapter.count}"

        //是否显示查看原图
        isOriginalImageOk()
    }

    /**
     * 判断是否有原图缓存，进行查看原图按钮的显示
     */
    private fun isOriginalImageOk() {
        val mSavePhoto = photoPreviewAdapter.getItem(currentPosition)
        if (mSavePhoto.imageUrl.isNotEmpty() && mSavePhoto.imageUrl != mSavePhoto.objURL) {
            val futureTarget =
                GlideApp.with(act).asFile().load(mSavePhoto.imageUrl).onlyRetrieveFromCache(true)
                    .submit()
            Flowable.just<FutureTarget<File>>(futureTarget)
                .map<File> { it.get() }
                .compose(RxSchedulers.io_main())
                .subscribe(object : ABaseSubscriber<File>() {
                    override fun onSuccess(t: File) {
                        GlideApp.with(act).clear(futureTarget)
                        showMaxImage.value = false
                    }

                    override fun onError(err: String?) {
                        GlideApp.with(act).clear(futureTarget)
                        showMaxImage.value = true
                    }
                })
        } else {
            showMaxImage.value = false
        }
    }

    /**
     * 现在显示原图
     */
    fun onDownloadOriginalImage() {

        val mSavePhoto = photoPreviewAdapter.getItem(currentPosition)

        if (mSavePhoto.imageUrl.isEmpty()) {
            return
        }
        val futureTarget = GlideApp.with(act).asFile().load(mSavePhoto.imageUrl).submit()
        //图片下载完成了。刷新当前的图片
        Flowable.just<FutureTarget<File>>(futureTarget)
            .map<File> { it.get() }
            .compose(RxSchedulers.io_main())
            .doOnSubscribe { subscription ->
                customLoadingDialog.start().setOnDismissListener {
                    GlideApp.with(act).clear(futureTarget)
                    subscription.cancel()
                }
            }
            .subscribe(object : ABaseSubscriber<File>() {
                override fun onSuccess(t: File) {
                    GlideApp.with(act).clear(futureTarget)
                    customLoadingDialog.dismiss()
                    //下载完成，通知刷新
                    photoPreviewAdapter.setUpdatePage(currentPosition)
                    //隐藏下载原图按钮
                    showMaxImage.value = false
                }

                override fun onError(err: String?) {
                    customLoadingDialog.dismiss()
                    ToastUtils.showShort(err)
                    GlideApp.with(act).clear(futureTarget)
                }
            })

    }

    /**
     * 保存图片
     */
    fun saveImage() {
        rxPermissions.request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
            .`as`(act.bindLifecycle())
            .subscribe { t ->
                if (t) {
                    //获取权限成功
                    val mSavePhoto = photoPreviewAdapter.getItem(currentPosition)
                    //先尝试保存大图
                    saveMaxImage(mSavePhoto)
                } else {
                    //获取权限失败
                    ToastUtils.showShort("缺少SD卡权限，保存图片失败")
                }
            }
    }


    /**
     * 保存当前图片
     */
    private fun saveMaxImage(previewBean: PhotoPreviewBean) {

        //判断是否有原图的缓存，有的话，直接保存原图，如果没有，则保存当前图片  （从缓存中）
        if (!StringUtils.isEmpty(previewBean.imageUrl) && previewBean.imageUrl != previewBean.objURL) {
            //进行原图缓存的判断
            val futureTarget =
                GlideApp.with(act).asFile().load(previewBean.imageUrl).onlyRetrieveFromCache(true).submit()

            Flowable.just<FutureTarget<File>>(futureTarget)
                .flatMap(object : Function<FutureTarget<File>, Publisher<String>> {
                    override fun apply(t: FutureTarget<File>): Publisher<String> {
                        if (t.get() != null) {
                            val tis = LibUtils.saveFileToDisk(t.get(), previewBean.imageUrl, act)
                            return Flowable.just<String>(tis)
                        }
                        return Flowable.error<String>(Throwable("没有原图片缓存"))
                    }
                })
                .compose(RxSchedulers.io_main())
                .doOnSubscribe { subscription ->
                    customLoadingDialog.start().setOnDismissListener {
                        GlideApp.with(act).clear(futureTarget)
                        subscription.cancel()
                    }
                }
                .subscribe(object : ABaseSubscriber<String>() {
                    override fun onSuccess(t: String) {
                        GlideApp.with(act).clear(futureTarget)
                        customLoadingDialog.dismiss()
                        ToastUtils.showShort(t)
                    }

                    override fun onError(err: String?) {
                        GlideApp.with(act).clear(futureTarget)
                        //图片没有缓存，显示下载原图，加载当前图片
                        saveMinPhoto(previewBean)
                    }
                })
        } else {
            saveMinPhoto(previewBean)
        }

    }


    //保存当前图片
    private fun saveMinPhoto(previewBean: PhotoPreviewBean) {
        //获取原图片的file（后台线程）
        val fileFutureTarget = GlideApp.with(act)
            .asFile()
            .load(previewBean.objURL)
            .onlyRetrieveFromCache(true)
            .submit()
        Flowable.just<FutureTarget<File>>(fileFutureTarget)
            .flatMap(object : Function<FutureTarget<File>, Publisher<String>> {
                override fun apply(t: FutureTarget<File>): Publisher<String> {
                    if (t.get() != null) {
                        val tis = LibUtils.saveFileToDisk(t.get(), previewBean.objURL!!, act)
                        return Flowable.just<String>(tis)
                    }
                    return Flowable.error<String>(Throwable("没有原图片缓存"))
                }
            })
            .compose(RxSchedulers.io_main())
            .doOnSubscribe { subscription ->
                customLoadingDialog.start().setOnDismissListener {
                    GlideApp.with(act).clear(fileFutureTarget)
                    subscription.cancel()
                }
            }
            .subscribe(object : ABaseSubscriber<String>() {
                override fun onSuccess(t: String) {
                    GlideApp.with(act).clear(fileFutureTarget)
                    customLoadingDialog.dismiss()
                    ToastUtils.showShort(t)
                }

                override fun onError(err: String?) {
                    GlideApp.with(act).clear(fileFutureTarget)
                    customLoadingDialog.dismiss()
                    ToastUtils.showShort("保存失败")
                }
            })
    }


}