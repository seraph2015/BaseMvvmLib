package org.seraph.lib.network.exception

import com.blankj.utilcode.util.StringUtils

/**
 * 网络请求异常信息处理
 * date：2019/4/19 13:47
 * author：xiongj
 * mail：417753393@qq.com
 **/
object ServerErrorCode {

    /**
     * 显示错误信息
     */
    @JvmStatic
    fun errorCodeToMessageShow(e: Throwable?): String {
        var message: String? = null
        if (e != null) {
            message = e.message
            if (e is retrofit2.HttpException) {
                val httpException = e as retrofit2.HttpException?
                message = "网络异常 " + httpException!!.code()
            } else { //统一提示网络异常
                if (StringUtils.isEmpty(message)) {
                    message = "网络异常"
                }
            }
            e.printStackTrace()
        }
        return if (message.isNullOrBlank()) "未知异常" else message
    }
}