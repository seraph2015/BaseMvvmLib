package org.seraph.lib.utlis

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.bumptech.glide.load.engine.GlideException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.ExecutionException


/**
 * 网络请求异常信息处理，显示错误信息
 */
fun Throwable?.onCodeToMessage(): String {
    val message: String? = when (this) {
        is HttpException -> "网络异常:${this.code()}"
        is UnknownHostException -> "解析服务器IP失败,请检查网络"
        is ExecutionException -> {
            val t = this.cause
            if (t is GlideException) "获取图片失败,请检查网络" else t?.message
        }
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

/**
 * text文本所有关键字变色
 */
fun String?.setKeyWordColor(keyWord: String?, color: Int = Color.BLUE): CharSequence {

    if (this.isNullOrEmpty()) {
        return ""
    }
    if (keyWord.isNullOrEmpty()) {
        return this
    }

    // 获取关键字所有的开始下标
    val ints = this.getStartList(keyWord)

    if (ints.isEmpty()) {
        return this
    }

    val style = SpannableStringBuilder(this)
    for (i in ints) {
        style.setSpan(ForegroundColorSpan(color), i, i + keyWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return style
}


/**
 * 获取开始下标集合
 */
fun String.getStartList(keyWord: String): List<Int> {
    val ints = ArrayList<Int>()
    //取反，保证第一次从0开始查找
    var tempStart = keyWord.length.inv() + 1
    do {
        //如果找到了，则更新下次查找位置开始
        tempStart = this.indexOf(keyWord, tempStart + keyWord.length)
        if (tempStart != -1) {
            ints.add(tempStart)
        }
    } while (tempStart != -1)

    return ints
}
