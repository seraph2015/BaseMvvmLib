package org.seraph.lib.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 解决输入法弹框问题 属性失效布局
 * date：2015/12/1 13:24
 * author：xiongj
 * mail：417753393@qq.com
 */
class CustomInsetsFrameLayout constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {

    private val insets = IntArray(4)

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    init {
        fitsSystemWindows = true
    }


    override fun fitSystemWindows(insets: Rect): Boolean {
        // 故意不要修改底部插图。 因为某些原因，如果底部插入被修改，窗口调整大小停止工作。
        this.insets[0] = insets.left
        this.insets[1] = insets.top
        this.insets[2] = insets.right

        insets.left = 0
        insets.top = 0
        insets.right = 0
        return super.fitSystemWindows(insets)
    }
}
