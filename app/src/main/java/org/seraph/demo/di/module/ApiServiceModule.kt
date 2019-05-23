package org.seraph.demo.di.module

import dagger.Module
import dagger.Provides
import org.seraph.demo.data.network.service.ApiBaiduService
import org.seraph.demo.data.network.service.ApiYiYanService
import org.seraph.lib.network.ApiBuild
import javax.inject.Singleton

/**
 * 网络接口注册
 * date：2019/4/18 15:26
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
object ApiServiceModule {

    @Provides
    @Singleton
    @JvmStatic
    fun apiBaiduService(apiBuild: ApiBuild): ApiBaiduService {
        return apiBuild.buildApiInterface(ApiBaiduService.BASE_URL, ApiBaiduService::class.java)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun apiYiYanService(apiBuild: ApiBuild): ApiYiYanService {
        return apiBuild.buildApiInterface(ApiYiYanService.BASE_URL, ApiYiYanService::class.java)
    }

}