package org.seraph.bcy.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.seraph.bcy.di.module.act.BcyILVmModule
import org.seraph.bcy.di.module.act.BcyShVmModule
import org.seraph.bcy.di.module.act.MainVmModule
import org.seraph.bcy.ui.BcyILActivity
import org.seraph.bcy.ui.BcyShActivity
import org.seraph.bcy.ui.MainActivity
import org.seraph.lib.di.module.LibActivityBindingModule
import org.seraph.lib.di.scope.ActivityScope

/**
 * 界面注册
 * date：2019/4/18 15:22
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = [LibActivityBindingModule::class])
internal abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainVmModule::class])
    abstract fun contributeMainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [BcyShVmModule::class])
    abstract fun contributeBcyShActivityInjector(): BcyShActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [BcyILVmModule::class])
    abstract fun contributeBcyILActivityInjector(): BcyILActivity
}