package org.seraph.module_image_search.data.repository

import org.seraph.lib.ui.base.ABaseRepository
import org.seraph.module_image_search.data.network.service.ApiGanKService
import org.seraph.module_image_search.ui.b.CategoryDataBean
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 其它数据
 * date：2019/4/28 17:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
class OtherRepository @Inject constructor(
    private val apiGanKService: ApiGanKService
) : ABaseRepository() {


    /**
     * 测试
     */
    suspend fun doSearch2(
        page: Int,
        count: Int
    ): List<CategoryDataBean> {
        return apiGanKService.doCategoryAsync(page, count).data
    }

}