package org.seraph.demo.data.repository

import org.seraph.demo.data.network.service.ApiBaiduService
import org.seraph.demo.data.network.service.ApiYiYanService
import org.seraph.demo.ui.main.b.ImageBaiduBean
import org.seraph.demo.ui.welcome.b.YiYanBean
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
    private val apiYiYanService: ApiYiYanService,
    private val apiBaiduService: ApiBaiduService
) : ABaseRepository() {



    /**
     * 获取一句话
     */
    suspend fun getYiYan(): YiYanBean {
        return apiIoCall { apiYiYanService.getYiYanAsync("?c=").await() }
    }


    /**
     * 度娘搜索图片
     */
    suspend fun doSearch(pageNo: Int, pageSize: Int, keyWordStr: String): List<ImageBaiduBean.BaiduImage> {
        return apiIoCall {
            apiBaiduService.doSearchAsync(
                "resultjsonavatarnew",
                keyWordStr,
                (pageNo - 1) * pageSize,
                pageSize
            ).await().imgs
        }
    }


}