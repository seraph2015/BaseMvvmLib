package org.seraph.demo.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.seraph.demo.di.module.act.GuideVmModule
import org.seraph.demo.di.module.act.MainVmModule
import org.seraph.demo.di.module.act.WelcomeVmModule
import org.seraph.demo.ui.main.MainActivity
import org.seraph.demo.ui.welcome.GuideActivity
import org.seraph.demo.ui.welcome.WelcomeActivity
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
    @ContributesAndroidInjector(modules = [WelcomeVmModule::class])
    abstract fun contributeWelcomeActivityInjector(): WelcomeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GuideVmModule::class])
    abstract fun contributeGuideActivityInjector(): GuideActivity


}