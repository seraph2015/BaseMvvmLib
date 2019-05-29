package org.seraph.lib.utlis

import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * 网络请求异常信息处理
 * date：2019/4/19 13:47
 * author：xiongj
 * mail：417753393@qq.com
 **/

/**
 * 显示错误信息
 */
fun Throwable?.onCodeToMessage(): String {
    val message: String? = when (this) {
        is HttpException -> "网络异常:${this.code()}"
        is UnknownHostException -> "解析服务器IP失败,请检查网络"
        else -> this?.message
    }
    return if (message.isNullOrBlank()) "未知异常" else message
}


/**
 * 去掉指定开头和结尾之间的字符串
 */
fun String.onStrReplace(beginStr: String, endStr: String): String {
    val begin = this.indexOf(beginStr)
    if (begin == -1) {
        return this
    }
    val end = this.lastIndexOf(endStr)
    if (end == -1) {
        return this
    }
    return if (begin > end) {
        this
    } else this.replace(this.substring(begin, end + 1), "")
}