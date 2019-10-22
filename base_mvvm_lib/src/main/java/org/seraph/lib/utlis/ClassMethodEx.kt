package org.seraph.lib.utlis

import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.*
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.load.engine.GlideException
import retrofit2.HttpException
import java.io.IOException
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
        style.setSpan(
            ForegroundColorSpan(color),
            i,
            i + keyWord.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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


/**
 * 图片绝对路径，读取图片的旋转的角度
 * @return 图片的旋转角度
 */
fun String?.getBitmapDegree(): Int {
    var degree = 0
    try {
        // 从指定路径下读取图片，并获取其EXIF信息
        val exifInterface = ExifInterface(this)
        // 获取图片的旋转信息
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90

            ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180

            ExifInterface.ORIENTATION_ROTATE_270 ->
                degree = 270

        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return degree
}


/**
 * 去掉html文本里面的超级链接<a></a>
 */
fun String.html2Txt(): String {
    //去掉超级链接;;
    return this.replace("<a[^>]*>[\\s\\S]*?</a>(?i)".toRegex(), "")
}

/**
 * 去掉所有的空格符号
 */
fun String.html2Txt2(): String {
    var html = this
    html = html.replace("<head>[\\s\\S]*?</head>(?i)".toRegex(), "")//去掉head
    html = html.replace("<!--[\\s\\S]*?-->".toRegex(), "")//去掉注释
    html = html.replace("<![\\s\\S]*?>".toRegex(), "")
    html = html.replace("<style[^>]*>[\\s\\S]*?</style>(?i)".toRegex(), "")//去掉样式
    html = html.replace("<script[^>]*>[\\s\\S]*?</script>(?i)".toRegex(), "")//去掉js
    html = html.replace("<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)".toRegex(), "")//去掉word标签
    html = html.replace("<xml>[\\s\\S]*?</xml>(?i)".toRegex(), "")
    html = html.replace("<html[^>]*>|<body[^>]*>|</html>|</body>(?i)".toRegex(), "")
    html = html.replace("\r\n|\n|\r|\t".toRegex(), "")//去掉换行
    html = html.replace("<br[^>]*>(?i)".toRegex(), "")//去掉换行
    html = html.replace("</p>(?i)".toRegex(), "")//去掉换行
    html = html.replace("<[^>]+>".toRegex(), "") //去掉所有的<>标签
    html = html.replace("&ldquo;".toRegex(), "\\“")
    html = html.replace("&rdquo;".toRegex(), "\\”")
    html = html.replace("&mdash;".toRegex(), "\\-")
    html = html.replace("&nbsp;".toRegex(), "")
    html = html.replace("&hellip;".toRegex(), "\\...")
    html = html.replace("&middot;".toRegex(), "\\·")
    return html
}


/**
 * 跳转qq客服
 */
fun Context.startQQCustomerService(qq: String) {
    // 跳转之前，可以先判断手机是否安装QQ
    if (this.isQQClientAvailable()) {
        // 跳转到客服的QQ
        try {
            this.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("mqqwpa://im/chat?chat_type=crm&uin=$qq")
                )
            )
        } catch (ex: ActivityNotFoundException) {
            ToastUtils.showShort("打开QQ客户端失败")
        }

    } else {
        ToastUtils.showShort("您还未安装QQ客户端")
    }
}

/**
 * 判断 用户是否安装QQ客户端
 */
private fun Context.isQQClientAvailable(): Boolean {
    val packageManager = this.packageManager
    val pinfo = packageManager.getInstalledPackages(0)
    if (pinfo != null) {
        for (i in pinfo.indices) {
            val pn = pinfo[i].packageName
            if (pn.equals(
                    "com.tencent.qqlite",
                    ignoreCase = true
                ) || pn.equals("com.tencent.mobileqq", ignoreCase = true)
            ) {
                return true
            }
        }
    }
    return false
}


/**
 * 设置窗口透明度
 */
fun Activity.setWindowAlpha(alpha: Float) {
    var mAlpha = alpha
    // 1. 设置半透明主题
    val lp = this.window.attributes
    // 2. 设置window的alpha值 (0.0 - 1.0)
    if (mAlpha < 0) {
        mAlpha = 0f
    } else if (mAlpha > 1) {
        mAlpha = 1f
    }
    lp.alpha = mAlpha
    this.window.attributes = lp
}


/**
 * 复制到粘贴扳
 */
fun Context.copyTextToClip(textStr: String) {
    val clipboardManager: ClipboardManager =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.primaryClip = ClipData.newPlainText(null, textStr)
}

/**
 * 获取控件的宽/高
 */
fun View?.getViewHeight(isHeight: Boolean): Int {
    val result: Int
    if (this == null) return 0
    result = if (isHeight) {
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        this.measure(h, 0)
        this.measuredHeight
    } else {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        this.measure(0, w)
        this.measuredWidth
    }
    return result
}

/**
 * 动态变化view的高/宽度
 */
fun View.onAnimationView(
    startValue: Int,
    endValue: Int,
    duration: Long = 500,
    isHeight: Boolean = true
) {
    val intEvaluator = IntEvaluator()
    //将动画值限定在(1,100)之间
    val valueAnimator = ValueAnimator.ofInt(1, 100)
    //动画持续时间
    valueAnimator.duration = duration
    valueAnimator.addUpdateListener {

        //得到当前瞬时的动画值,在(1,100)之间
        val currentAnimatedValue = it.animatedValue as Int
        //计算得到当前系数fraction
        val fraction = currentAnimatedValue / 100f

        //评估出当前的高度其设置
        if (isHeight) {
            this.layoutParams.height = intEvaluator.evaluate(fraction, startValue, endValue)!!
        } else {
            this.layoutParams.width = intEvaluator.evaluate(fraction, startValue, endValue)!!
        }

        this.requestLayout()
    }
    valueAnimator.start()
}
