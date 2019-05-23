package org.seraph.lib.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils

/**
 * 状态栏等高的view
 * date：2019/3/28 16:21
 * author：xiongj
 * mail：417753393@qq.com
 */
class StatusBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {


    private var statusBarHeight: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (statusBarHeight == 0) {
            statusBarHeight = BarUtils.getStatusBarHeight()
        }
        setMeasuredDimension(View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), statusBarHeight)
    }
}
