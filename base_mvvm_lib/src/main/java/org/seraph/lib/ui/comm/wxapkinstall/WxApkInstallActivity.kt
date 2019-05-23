package org.seraph.lib.ui.comm.wxapkinstall

import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.StringUtils
import org.seraph.lib.R
import org.seraph.lib.databinding.ActWxApkInstallBinding
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.view.NoDataView

/**
 * 微信.apk.1安装
 * date：2019/5/9 10:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WxApkInstallActivity : ABaseActivity<ActWxApkInstallBinding, WxApkInstallVm>(R.layout.act_wx_apk_install) {

    override fun getViewModelClass(): Class<WxApkInstallVm> {
        return WxApkInstallVm::class.java
    }

    override fun init() {
        binding.vm = vm
        BarUtils.setStatusBarLightMode(this, false)
        initToolbar(binding.tbAppInstall, false)
        binding.ndv.setOnClickListener { vm.onRequestPermissions() }
        vm.start()
    }

    /**
     * 设置加载状态
     */
    fun setNoDataInfo(type: Int, info: String) {
        if (!StringUtils.isEmpty(info)) {
            binding.ndv.setNoDataMsg(info)
        }
        when (type) {
            NoDataView.LOADING_OK -> binding.ndv.setLoadingOk()
            NoDataView.NO_DATE -> binding.ndv.setLoadingOk()
        }

    }
}