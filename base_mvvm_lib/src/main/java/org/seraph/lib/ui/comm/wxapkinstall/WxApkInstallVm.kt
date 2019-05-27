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
import org.seraph.lib.LibConfig
import org.seraph.lib.R
import org.seraph.lib.utlis.RxSchedulers
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.view.NoDataView
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

    /**
     * apk提示信息
     */
    val installTis: MutableLiveData<String>  by lazy {
        MutableLiveData<String>()
    }

    /**
     * apk其它信息
     */
    val apkOtherInfo: MutableLiveData<String>  by lazy {
        MutableLiveData<String>()
    }


    init {
        wxPathStr = activity.intent.data?.path
        rxPermissions = RxPermissions(activity)
    }

    override fun start() {
        onRequestPermissions()
        appInfo.observeForever {
            if (it != null) {
                installTis.value = if (AppUtils.isAppInstalled(it.packageName)) "是否覆盖安装此应用？" else "是否继续安装此应用？"
                apkOtherInfo.value = "应用包名：${it.packageName}\n\n应用版本：${it.versionName}\n\n应用位置：${it.packagePath}"
            }
        }
    }

    /**
     * 请求读写权限
     */
    fun onRequestPermissions() {
        //请求权限
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .map<File> { aBoolean ->
                if (aBoolean) {
                    return@map initApkPath()
                } else {
                    return@map null
                }
            }
            .compose(RxSchedulers.io_main_o())
            .`as`(activity.bindLifecycle())
            .subscribe({ file ->
                activity.setNoDataInfo(NoDataView.LOADING_OK, "")
                //获取app信息
                appInfo.value = AppUtils.getApkInfo(file)
            },
                { activity.setNoDataInfo(NoDataView.NO_DATE, "加载程序失败") }
            )
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

    fun onApkInstall() {
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