package org.seraph.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.seraph.demo.AppConfig
import org.seraph.demo.data.network.service.ApiBaiduService
import org.seraph.demo.data.network.service.ApiYiYanService
import org.seraph.lib.network.ApiBuild
import javax.inject.Singleton


/**
 * app全局单例注册
 * date：2019/4/18 15:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun apiBaiduService(apiBuild: ApiBuild): ApiBaiduService {
        return apiBuild.buildApiInterface(ApiBaiduService.BASE_URL, AppConfig.DEBUG)
    }

    @Provides
    @Singleton
    fun apiYiYanService(apiBuild: ApiBuild): ApiYiYanService {
        return apiBuild.buildApiInterface(ApiYiYanService.BASE_URL, AppConfig.DEBUG)
    }
}