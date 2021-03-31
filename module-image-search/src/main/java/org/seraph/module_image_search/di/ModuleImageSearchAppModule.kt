package org.seraph.module_image_search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seraph.lib.network.ApiBuild
import org.seraph.module_image_search.SearchImageConstants
import org.seraph.module_image_search.data.network.service.ApiGanKService
import javax.inject.Singleton


/**
 * app全局单例注册
 * date：2019/4/18 15:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
@InstallIn(SingletonComponent::class)
object ModuleImageSearchAppModule {

    @Provides
    @Singleton
    fun apiGanKService(
        apiBuild: ApiBuild
    ): ApiGanKService {
        return apiBuild.buildApiInterface(
            ApiGanKService.BASE_URL,
            SearchImageConstants.DEBUG
        )
    }

}