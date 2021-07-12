package org.seraph.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seraph.demo.LoveConstants
import org.seraph.demo.data.network.service.ApiYiYanService
import org.seraph.lib.network.ApiBuild
import javax.inject.Singleton


/**
 * app全局单例注册
 **/
@Module
@InstallIn(SingletonComponent::class)
object LoveAppModule {

    @Provides
    @Singleton
    fun apiYiYanService(
        apiBuild: ApiBuild
    ): ApiYiYanService {
        return apiBuild.buildApiInterface(
            ApiYiYanService.BASE_URL,
            LoveConstants.DEBUG
        )
    }

}