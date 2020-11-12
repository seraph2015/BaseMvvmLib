package org.seraph.module_welcome.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.seraph.lib.network.ApiBuild
import org.seraph.module_welcome.WelcomeConstants
import org.seraph.module_welcome.network.service.ModuleWelcomeService
import javax.inject.Singleton


/**
 * 单例注册
 **/
@Module
@InstallIn(ApplicationComponent::class)
object ModuleWelcomeModule {

    @Provides
    @Singleton
    fun moduleWelcomeServiceService(apiBuild: ApiBuild): ModuleWelcomeService {
        return apiBuild.buildApiInterface(ModuleWelcomeService.BASE_URL, WelcomeConstants.DEBUG)
    }
}