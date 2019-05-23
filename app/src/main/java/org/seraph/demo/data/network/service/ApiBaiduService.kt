package org.seraph.demo.data.network.service

import io.reactivex.Flowable
import org.seraph.demo.ui.main.b.ImageBaiduBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET
    fun doBaiduImageUrl(@Url url: String): Flowable<ImageBaiduBean>

    /**
     * @param tn       固定参数 resultjsonavatarnew
     * @param word     搜索关键字
     * @param start    开始位置
     * @param pageSize 反正数据条数
     */
    @GET("search/avatarjson")
    fun doSearch(@Query("tn") tn: String, @Query("word") word: String, @Query("pn") start: Int?, @Query("rn") pageSize: Int?): Flowable<ImageBaiduBean>


}