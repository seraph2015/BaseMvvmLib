package org.seraph.lib.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import org.seraph.lib.R


/**
 * 横向动态流布局
 */
class DynamicLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val columnSpacing: Int
    private val rowSpacing: Int

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.DynamicLayout)
        columnSpacing = array.getDimensionPixelSize(R.styleable.DynamicLayout_columnSpacing, 0)
        rowSpacing = array.getDimensionPixelSize(R.styleable.DynamicLayout_rowSpacing, 0)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        var height = 0
        var tempWidth = 0
        var tempHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) {
                child.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY)
                )
            } else {
                child.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.getSize(widthMeasureSpec),
                        View.MeasureSpec.AT_MOST
                    ), View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.getSize(heightMeasureSpec),
                        View.MeasureSpec.UNSPECIFIED
                    )
                )
            }

            val srcWidth = if (child.measuredWidth >= width)
                width
            else
                child
                    .measuredWidth
            tempWidth += srcWidth + if (i == 0) 0 else columnSpacing
            if (tempWidth > width) {
                height += tempHeight + rowSpacing
                tempHeight = child.measuredHeight
                tempWidth = srcWidth
            } else if (child.measuredHeight > tempHeight)
                tempHeight = child.measuredHeight
        }
        height += tempHeight + rowSpacing
        if (layoutParams != null && layoutParams.height == -2)
            setMeasuredDimension(width, height)
        else
            setMeasuredDimension(width, View.MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var tempHeight = 0
        var tempT = 0
        var tempL = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val width = if (child.measuredWidth >= width)
                width
            else
                child.measuredWidth
            tempL += if (i == 0) 0 else columnSpacing
            if (tempL + width <= getWidth()) {
                child.layout(
                    tempL, tempT, tempL + width,
                    tempT + child.measuredHeight
                )
                tempL += width
                if (tempHeight < child.measuredHeight)
                    tempHeight = child.measuredHeight
            } else {
                tempL = 0
                tempT += tempHeight + rowSpacing
                tempHeight = child.measuredHeight
                child.layout(
                    tempL, tempT, tempL + width,
                    tempT + child.measuredHeight
                )
                tempL = width
            }
        }
    }
}
