package org.seraph.module_image_search.data.network.service

import org.seraph.module_image_search.ui.b.CategoryBean
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiGanKService {

    companion object {

        const val BASE_URL: String = "https://gank.io/api/v2/"
    }

    /**
     * 获取指定分类下数据
     */
    @GET("data/category/Girl/type/Girl/page/{page}/count/{count}")
    suspend fun doCategoryAsync(
        @Path("page") page: Int,
        @Path("count") count: Int
    ): CategoryBean

}