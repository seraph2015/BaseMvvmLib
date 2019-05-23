package org.seraph.lib.utlis

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * view属性设置绑定扩展
 * date：2019/4/23 16:04
 * author：xiongj
 * mail：417753393@qq.com
 **/

object ViewBindingExt {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, type: String) {
        when (type) {
            "visible" -> view.visibility = View.VISIBLE
            "gone" -> view.visibility = View.GONE
            "invisible" -> view.visibility = View.INVISIBLE
        }

    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        if (drawable != null) {
            view.setImageDrawable(drawable)
        }
    }


}