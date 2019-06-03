package org.seraph.lib.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.seraph.lib.di.module.act.PhotoPreviewVmModule
import org.seraph.lib.di.module.act.WxApkInstallVmModule
import org.seraph.lib.di.scope.ActivityScope
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewActivity
import org.seraph.lib.ui.comm.wxapkinstall.WxApkInstallActivity

/**
 * 界面注册绑定
 * date：2019/4/18 15:22
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
abstract class LibActivityBindingModule {


    @ActivityScope
    @ContributesAndroidInjector(modules = [PhotoPreviewVmModule::class])
    abstract fun contributePhotoPreviewActivityInjector(): PhotoPreviewActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [WxApkInstallVmModule::class])
    abstract fun contributeWxApkInstallActivityInjector(): WxApkInstallActivity

}