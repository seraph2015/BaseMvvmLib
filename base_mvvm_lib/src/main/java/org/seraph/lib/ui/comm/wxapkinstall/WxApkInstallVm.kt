package org.seraph.lib.ui.comm.wxapkinstall

import android.Manifest
import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.seraph.lib.LibConfig
import org.seraph.lib.R
import org.seraph.lib.ui.base.ABaseViewModel
import java.io.File
import javax.inject.Inject

/**
 * wxapkinstall
 * date：2019/5/9 10:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WxApkInstallVm @Inject constructor(
    application: Application,
    val activity: WxApkInstallActivity
) : ABaseViewModel(application) {

    /**
     * apk路径
     */
    private var wxPathStr: String? = null

    /**
     * 权限请求
     */
    private var rxPermissions: RxPermissions

    /**
     * apk信息
     */
    val appInfo: MutableLiveData<AppUtils.AppInfo>  by lazy {
        MutableLiveData<AppUtils.AppInfo>()
    }


    init {
        wxPathStr = activity.intent.data?.path
        rxPermissions = RxPermissions(activity)
    }

    override fun start() {
        onRequestPermissions()
    }

    private var disposable: Disposable? = null

    /**
     * 请求读写权限
     */
    fun onRequestPermissions() {
        //请求权限
        disposable = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe {
                launchOnUI {
                    appInfo.value = withContext(Dispatchers.IO) {
                        //获取app信息
                        return@withContext if (it) AppUtils.getApkInfo(initApkPath()) else null
                    }
                }
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }


    //初始化获取apk路径
    private fun initApkPath(): File? {
        //腾讯文件跳转，获取文件路径判断是否为apk.1
        if (wxPathStr != null && wxPathStr!!.contains("apk.1")) {
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
            if (FileUtils.copyFile(File(wxPathStr), apkFile)) {
                return apkFile
            }
        }
        return null
    }

    private fun onApkInstall() {
        AppUtils.installApp(appInfo.value!!.packagePath)
        activity.finish()
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_cancel -> activity.onBackPressed()
            R.id.tv_install -> onApkInstall()
        }
    }
}