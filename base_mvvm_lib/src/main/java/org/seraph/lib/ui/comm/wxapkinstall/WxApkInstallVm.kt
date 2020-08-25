package org.seraph.lib.ui.comm.wxapkinstall

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.seraph.lib.LibConfig
import org.seraph.lib.R
import org.seraph.lib.ui.base.ABaseViewModel
import java.io.File

/**
 * wxapkinstall
 * date：2019/5/9 10:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WxApkInstallVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ABaseViewModel() {

    /**
     * apk路径
     */
    private var wxPathStr: String? = null

    /**
     * apk信息
     */
    val appInfo: MutableLiveData<AppUtils.AppInfo> by lazy {
        MutableLiveData<AppUtils.AppInfo>()
    }

    override fun start(vararg any: Any?) {
        wxPathStr = any[0] as String?

        onRequestPermissions()
    }

    /**
     * 请求读写权限
     */
    fun onRequestPermissions() {
        //请求读写权限
        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    launchOnUI {
                        appInfo.value = withContext(Dispatchers.IO) {
                            //获取app信息
                            return@withContext AppUtils.getApkInfo(initApkPath())
                        }
                    }
                }

                override fun onDenied() {
                    appInfo.value = null
                }
            }).request()
    }


    //初始化获取apk路径
    private fun initApkPath(): File? {
        if (wxPathStr == null) {
            return null
        }
        //腾讯文件跳转，获取文件路径判断是否为apk.1
        if (wxPathStr!!.contains("apk.1")) {
            //判断下载文件夹是否有对应的apk
            wxPathStr = wxPathStr!!.replace("/external", PathUtils.getExternalStoragePath())
        }
        //新apk位置
        val apkPath =
            PathUtils.getExternalDownloadsPath() + "/" + LibConfig.APP_NAME + "/" + EncryptUtils.encryptMD5ToString(
                wxPathStr
            ) + ".apk"
        val apkFile = File(apkPath)
        if (FileUtils.isFileExists(apkFile) && apkFile.length() > 0) {
            return apkFile
        } else {
            if (FileUtils.copy(File(wxPathStr!!), apkFile)) {
                return apkFile
            }
        }
        return null
    }

    private fun onApkInstall() {
        AppUtils.installApp(appInfo.value!!.packagePath)
        onCloseActivity.value = 1
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_cancel -> onCloseActivity.value = 0
            R.id.tv_install -> onApkInstall()
        }
    }

}