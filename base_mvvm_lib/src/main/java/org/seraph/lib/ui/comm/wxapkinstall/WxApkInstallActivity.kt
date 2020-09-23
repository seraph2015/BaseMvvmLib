package org.seraph.lib.ui.comm.wxapkinstall

import androidx.activity.viewModels
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.seraph.lib.LibConfig
import org.seraph.lib.R
import org.seraph.lib.databinding.ActWxApkInstallBinding
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.view.NoDataView
import java.util.*

/**
 * 微信.apk.1安装
 * date：2019/5/9 10:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
@AndroidEntryPoint
class WxApkInstallActivity :
    ABaseActivity<ActWxApkInstallBinding, WxApkInstallVm>(R.layout.act_wx_apk_install) {

    override fun bindVM(): WxApkInstallVm {
        return viewModels<WxApkInstallVm>().value
    }

    override fun init() {
        if (!LibConfig.APP_WX_INSTALL) {
            finish()
        }
        binding.vm = vm
        BarUtils.setStatusBarLightMode(this, false)
        initToolbar(binding.tbAppInstall, null)
        vm.appInfo.observe(this, {
            if (it != null) {
                setNoDataInfo(NoDataView.LOADING_OK)
                binding.tvApkInstallTis.text =
                    if (AppUtils.isAppInstalled(it.packageName)) "是否覆盖安装此应用？" else "是否继续安装此应用？"
                binding.tvApkOtherInfo.text = String.format(
                    Locale.getDefault(),
                    "应用包名：%s\n\n应用版本：%s\n\n应用位置：%s",
                    it.packageName,
                    it.versionName,
                    it.packagePath
                )
            } else {
                setNoDataInfo(NoDataView.NO_DATE, "加载程序失败")
            }
        })
        binding.ndv.setOnClickListener { vm.onRequestPermissions() }
        vm.start(intent.data?.path)
    }

    /**
     * 设置加载状态
     */
    private fun setNoDataInfo(type: Int, info: String? = null) {
        if (!info.isNullOrEmpty()) {
            binding.ndv.setNoDataMsg(info)
        }
        when (type) {
            NoDataView.LOADING_OK -> binding.ndv.setLoadingOk()
            NoDataView.NO_DATE -> binding.ndv.setNoDate()
        }

    }

}