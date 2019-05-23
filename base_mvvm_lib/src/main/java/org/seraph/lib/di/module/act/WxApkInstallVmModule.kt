package org.seraph.lib.di.module.act

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.seraph.lib.di.vm.ViewModelKey
import org.seraph.lib.ui.comm.wxapkinstall.WxApkInstallVm

/**
 * 微信apk文件安装
 * date：2019/4/18 17:33
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
abstract class WxApkInstallVmModule {

    @Binds
    @IntoMap
    @ViewModelKey(WxApkInstallVm::class)
    abstract fun bindWxApkInstallVm(viewModel: WxApkInstallVm): ViewModel

}