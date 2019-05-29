package org.seraph.lib.ui.comm.photopreview

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.utlis.LibUtils
import org.seraph.lib.view.CustomLoadingDialog
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

    private var customLoadingDialog: CustomLoadingDialog

    init {
        //默认显示功能栏
        showBar.value = true

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


    /**
     * 更新显示当前位置的状态
     */
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
                GlideApp.with(act).asFile().load(mSavePhoto.imageUrl).onlyRetrieveFromCache(true).submit()
            launchOnUI({
                withContext(Dispatchers.IO) {
                    return@withContext futureTarget.get()
                }
                showMaxImage.value = false
            }, {
                showMaxImage.value = true
            }, {
                GlideApp.with(act).clear(futureTarget)
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
        val job = launchOnUI({
            withContext(Dispatchers.IO) {
                return@withContext futureTarget.get()
            }
            photoPreviewAdapter.setUpdatePage(currentPosition)
            showMaxImage.value = false
        }, {
            ToastUtils.showShort(it)
        }, {
            customLoadingDialog.dismiss()
        })
        customLoadingDialog.start().setOnDismissListener {
            GlideApp.with(act).clear(futureTarget)
            job.cancel()
        }
    }


    /**
     * 保存图片
     */
    fun saveImage() {
        //请求读写权限
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(object : PermissionUtils.SimpleCallback {

            override fun onGranted() {
                //获取权限成功
                val mSavePhoto = photoPreviewAdapter.getItem(currentPosition)
                //先尝试保存大图
                saveMaxImage(mSavePhoto)
            }

            override fun onDenied() {
                ToastUtils.showShort("缺少SD卡权限，保存图片失败")
            }
        }).request()
    }


    /**
     * 保存当前原图片
     */
    private fun saveMaxImage(previewBean: PhotoPreviewBean) {
        //判断是否有原图的缓存，有的话，直接保存原图，如果没有，则保存当前图片  （从缓存中）
        if (!StringUtils.isEmpty(previewBean.imageUrl) && previewBean.imageUrl != previewBean.objURL) {
            //进行原图缓存的判断
            val futureTarget =
                GlideApp.with(act).asFile().load(previewBean.imageUrl).onlyRetrieveFromCache(true).submit()
            val job = launchOnUI({
                val msg = withContext(Dispatchers.IO) {
                    return@withContext LibUtils.saveFileToDisk(futureTarget.get(), previewBean.imageUrl, act)
                }
                customLoadingDialog.dismiss()
                ToastUtils.showShort(msg)
            }, {
                GlideApp.with(act).clear(futureTarget)
                //图片没有缓存，显示下载原图，加载当前图片
                saveMinPhoto(previewBean)
            })
            customLoadingDialog.start().setOnDismissListener {
                GlideApp.with(act).clear(futureTarget)
                job.cancel()
            }
        } else {
            saveMinPhoto(previewBean)
        }

    }


    /**
     * 保存当前图片
     */
    private fun saveMinPhoto(previewBean: PhotoPreviewBean) {
        val fileFutureTarget = GlideApp.with(act)
            .asFile()
            .load(previewBean.objURL)
            .onlyRetrieveFromCache(true)
            .submit()
        val job = launchOnUI({
            //获取原图片的file（后台线程）
            val msg = withContext(Dispatchers.IO) {
                return@withContext LibUtils.saveFileToDisk(fileFutureTarget.get(), previewBean.objURL!!, act)
            }
            ToastUtils.showShort(msg)
        }, {
            ToastUtils.showShort("保存失败")
        }, {
            customLoadingDialog.dismiss()
        })
        customLoadingDialog.start().setOnDismissListener {
            GlideApp.with(act).clear(fileFutureTarget)
            job.cancel()
        }
    }


}