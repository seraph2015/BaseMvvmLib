package org.seraph.lib.utlis

import android.content.Context
import android.graphics.Typeface
import com.blankj.utilcode.util.StringUtils

/**
 * 设置字体工具
 * date：2017/7/31 15:04
 * author：xiongj
 * mail：417753393@qq.com
 */
object FontUtils {


    /*-----------------------------------更改系统字体代码部分开始-----------------------------*/
    fun replaceSystemDefaultFont(context: Context, fontPath: String) {
        if (!StringUtils.isEmpty(fontPath)) {
            //這里我们修改的是MoNOSPACE,是因为我们在主题里给app设置的默认字体就是monospace，设置其他的也可以
            replaceTypefaceField("MONOSPACE", createTypeface(context, fontPath))
        }
    }

    //通过字体地址创建自定义字体
    private fun createTypeface(context: Context, fontPath: String): Typeface {
        return Typeface.createFromAsset(context.assets, fontPath)
    }

    //关键--》通过修改MONOSPACE字体为自定义的字体达到修改app默认字体的目的
    private fun replaceTypefaceField(fieldName: String, value: Any) {
        //替换默认的字体和加粗字体
        try {
            val defaultField = Typeface::class.java.getDeclaredField(fieldName)
            defaultField.isAccessible = true
            defaultField.set(null, value)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    /*-----------------------------------更改系统字体代码部分结束-----------------------------*/


}
