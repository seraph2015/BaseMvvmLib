package org.seraph.lib.utlis

import android.content.Context
import android.graphics.Typeface

/**
 * 设置字体工具
 * date：2017/7/31 15:04
 * author：xiongj
 * mail：417753393@qq.com
 */

/*-----------------------------------更改系统字体代码部分开始-----------------------------*/
fun Context.replaceSystemDefaultFont(fontPath: String?) {
    if (!fontPath.isNullOrEmpty()) {
        //這里我们修改的是MoNOSPACE,是因为我们在主题里给app设置的默认字体就是monospace，设置其他的也可以
        "MONOSPACE".replaceTypefaceField(this.createTypeface(fontPath))
    }
}

//通过字体地址创建自定义字体
private fun Context.createTypeface(fontPath: String): Typeface {
    return Typeface.createFromAsset(this.assets, fontPath)
}

//关键--》通过修改MONOSPACE字体为自定义的字体达到修改app默认字体的目的
private fun String.replaceTypefaceField(value: Any) {
    //替换默认的字体和加粗字体
    try {
        val defaultField = Typeface::class.java.getDeclaredField(this)
        defaultField.isAccessible = true
        defaultField.set(null, value)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
/*-----------------------------------更改系统字体代码部分结束-----------------------------*/

