package org.seraph.lib.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import org.seraph.lib.R

/**
 * 自定义尺寸比例ImageView(宽为基准)
 * date：2019/4/22 10:11
 * author：xiongj
 * mail：417753393@qq.com
 **/
class CustomWHImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private var w = 1 // 宽比例
    private var h = 1 // 高比例

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomWHImageView
        )
        this.h = typedArray.getInt(R.styleable.CustomWHImageView_h, 1)
        this.w = typedArray.getInt(R.styleable.CustomWHImageView_w, 1)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec) - paddingRight - paddingLeft
        if (width > 0 && w > 0 && h > 0) {
            val newH = width * h / w
            setMeasuredDimension(width, newH)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }

    /**
     * 设置宽高比
     */
    fun setSize(w: Int, h: Int) {
        this.w = w
        this.h = h
        //布局改变完成，刷新一下布局
        requestLayout()
    }

}