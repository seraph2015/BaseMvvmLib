package org.seraph.demo.data.network.service

import kotlinx.coroutines.Deferred
import org.seraph.demo.ui.welcome.b.YiYanBean
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * 一言
 * date：2017/4/20 14:33
 * author：xiongj
 * mail：417753393@qq.com
 */
interface ApiYiYanService {

    companion object {
        const val BASE_URL = "https://v1.hitokoto.cn/"
    }


    /**
     * 随机获取一句话 （从7种分类中随机抽取）
     *
     * @param c      可选 (a - 动画 b – 漫画 c – 游戏 d – 小说 e – 原创f – 来自网络 g – 其他)
     * @param encode 可选 (text - 纯净文本  json - 不进行unicode转码的json文本  js-指定选择器(默认.hitokoto)的同步执行函数  默认 - 返回unicode转码的json文本 )
     */
    @GET
    fun getYiYanAsync(@Url url: String): Deferred<YiYanBean>


}
