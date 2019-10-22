package org.seraph.demo.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.seraph.demo.AppApplication
import org.seraph.demo.di.module.ActivityBindingModule
import org.seraph.demo.di.module.ApiServiceModule
import org.seraph.demo.di.module.AppModule
import javax.inject.Singleton

/**
 * org.seraph.demo.di
 * date：2019/5/23 11:28
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApiServiceModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    //@BindsInstance使得component可以在构建时绑定实例Application
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

}
