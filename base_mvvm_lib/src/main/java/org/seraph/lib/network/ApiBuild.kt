package org.seraph.lib.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.seraph.lib.LibConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * 网络请求构建
 * date：2019/4/19 10:59
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ApiBuild @Inject constructor() {

    fun builder(isDebug: Boolean = false): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        //log输出
        if (isDebug) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        builder.connectTimeout(LibConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        return builder
    }


    /**
     * 构建ApiInterface
     */
    inline fun <reified T : Any> buildApiInterface(
        apiBaseUrl: String,
        isDebug: Boolean = false
    ): T {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiBaseUrl)
            .client(builder(isDebug).build()).build()
            .create(T::class.java)
    }


}