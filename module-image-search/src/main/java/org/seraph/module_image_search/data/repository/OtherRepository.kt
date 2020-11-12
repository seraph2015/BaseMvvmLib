package org.seraph.module_image_search.data.repository

import org.seraph.module_image_search.data.network.service.ApiBaiduService
import org.seraph.module_image_search.ui.b.BaiduImage
import org.seraph.lib.ui.base.ABaseRepository
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
    private val apiBaiduService: ApiBaiduService
) : ABaseRepository() {


    /**
     * 度娘搜索图片
     */
    suspend fun doSearch(
        pageNo: Int,
        pageSize: Int,
        keyWordStr: String
    ): List<BaiduImage> {
        return apiBaiduService.doSearchAsync(
            word = keyWordStr,
            start = (pageNo - 1) * pageSize,
            pageSize = pageSize
        ).data

    }


}