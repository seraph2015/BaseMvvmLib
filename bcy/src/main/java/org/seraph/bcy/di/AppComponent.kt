package org.seraph.bcy.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.seraph.bcy.AppApplication
import org.seraph.bcy.di.module.ActivityBindingModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    //@BindsInstance使得component可以在构建时绑定实例Application
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
