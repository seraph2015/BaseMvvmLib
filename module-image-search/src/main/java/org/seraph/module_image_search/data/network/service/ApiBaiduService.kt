package org.seraph.module_image_search.data.network.service

import org.seraph.module_image_search.ui.b.ImageBaiduBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 度娘api
 * date：2019/4/19 11:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface ApiBaiduService {

    companion object {

        const val BASE_URL: String = "https://image.baidu.com/"
    }

    /**
     * 搜索百度图片
     * @param tn       固定参数 resultjsonavatarnew
     * @param word     搜索关键字
     * @param start    开始位置
     * @param pageSize 反正数据条数
     */
    @GET("search/acjson")
    suspend fun doSearchAsync(
        @Query("tn") tn: String = "resultjson_com",
        @Query("word") word: String,
        @Query("pn") start: Int?,
        @Query("rn") pageSize: Int?,
        @Query("catename") catename: String = "",
        @Query("ipn") ipn: String = ""
    ): ImageBaiduBean

}