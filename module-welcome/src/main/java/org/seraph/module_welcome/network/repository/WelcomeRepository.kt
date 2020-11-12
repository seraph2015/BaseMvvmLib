package org.seraph.module_welcome.network.repository

import org.seraph.module_welcome.network.service.ModuleWelcomeService
import org.seraph.module_welcome.ui.b.WelcomeYiYanBean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WelcomeRepository @Inject constructor(
    private val apiYiYanService: ModuleWelcomeService
) {

    /**
     * 获取一句话
     */
    suspend fun getYiYan(): WelcomeYiYanBean {
        return apiYiYanService.getYiYanAsync("?c=")
    }

}