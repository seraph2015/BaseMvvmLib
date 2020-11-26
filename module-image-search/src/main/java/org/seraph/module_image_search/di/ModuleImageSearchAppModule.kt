package org.seraph.module_image_search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.seraph.lib.network.ApiBuild
import org.seraph.module_image_search.SearchImageConstants
import org.seraph.module_image_search.data.network.service.ApiBaiduService
import javax.inject.Singleton


/**
 * app全局单例注册
 * date：2019/4/18 15:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
@InstallIn(ApplicationComponent::class)
object ModuleImageSearchAppModule {
    @Provides
    @Singleton
    fun apiBaiduService(
        apiBuild: ApiBuild
    ): ApiBaiduService {
        return apiBuild.buildApiInterface(
            ApiBaiduService.BASE_URL,
            SearchImageConstants.DEBUG
        )
    }

}