package org.seraph.lib.ui.comm.photopreview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.utlis.saveFileToDisk
import org.seraph.lib.view.CustomLoadingDialog

/**
 * 图片预览界面
 * date：2019/4/26 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
class PhotoPreviewVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    val act: PhotoPreviewActivity,
    var photoPreviewAdapter: PhotoPreviewAdapter
) : ABaseViewModel() {


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
        /**
         * 显示大图
         */
        const val SHOW_MAX_IMAGE = "showMaxImage"
        /**
         * 下载图片
         */
        const val DOWNLOAD_IMAGE = "downloadImage"
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
    fun upDateCurrentPosition() {
        //标题
        titleStr.value = "${act.currentPosition + 1}/${photoPreviewAdapter.count}"

        //是否可以查看原图
        if (act.showMaxImage) {
            //是否显示查看原图
            isOriginalImageOk()
        }
        //是否可以下载图片
        if (act.downloadImage) {
            //是否是本地图片，本地图片不显示下载
            showDownload.value =
                photoPreviewAdapter.getItem(act.currentPosition).fromType != IMAGE_TYPE_LOCAL
        }

    }

    /**
     * 判断是否有原图缓存，进行查看原图按钮的显示
     */
    private fun isOriginalImageOk() {

        val mSavePhoto = photoPreviewAdapter.getItem(act.currentPosition)
        if (mSavePhoto.imageUrl.isNotEmpty() && mSavePhoto.imageUrl != mSavePhoto.objURL) {
            val futureTarget =
                GlideApp.with(act).asFile().load(mSavePhoto.imageUrl).onlyRetrieveFromCache(true)
                    .submit()
            launchOnUI({
                withContext(Dispatchers.IO) {
                    return@withContext futureTarget.get()
                }
                showMaxImage.value = false
            }, {
                showMaxImage.value = true
            })
        } else {
            showMaxImage.value = false
        }
    }


    /**
     * 现在显示原图
     */
    fun onDownloadOriginalImage() {
        val mSavePhoto = photoPreviewAdapter.getItem(act.currentPosition)
        if (mSavePhoto.imageUrl.isEmpty()) {
            return
        }
        val futureTarget = GlideApp.with(act).asFile().load(mSavePhoto.imageUrl).submit()
        //图片下载完成了。刷新当前的图片
        val job = launchOnUI({
            withContext(Dispatchers.IO) {
                return@withContext futureTarget.get()
            }
            photoPreviewAdapter.setUpdatePage(act.currentPosition)
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
        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {

                override fun onGranted() {
                    //获取权限成功
                    val mSavePhoto = photoPreviewAdapter.getItem(act.currentPosition)
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
                GlideApp.with(act).asFile().load(previewBean.imageUrl).onlyRetrieveFromCache(true)
                    .submit()
            val job = launchOnUI({
                val msg = withContext(Dispatchers.IO) {
                    return@withContext act.saveFileToDisk(futureTarget.get(), previewBean.imageUrl)
                }
                customLoadingDialog.dismiss()
                ToastUtils.showShort(msg)
            }, {
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
                return@withContext act.saveFileToDisk(fileFutureTarget.get(), previewBean.objURL!!)
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