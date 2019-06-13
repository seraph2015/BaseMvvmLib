package org.seraph.lib.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.airbnb.lottie.LottieAnimationView
import com.blankj.utilcode.util.SizeUtils
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.internal.InternalAbstract

/**
 * 下拉刷新-（下拉头部动画）
 * date：2019/4/19 17:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
class LottieHeader @JvmOverloads protected constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    InternalAbstract(context, attrs, defStyleAttr), RefreshHeader {

    private var animator: ValueAnimator? = null

    private var mLottieAnimationView: LottieAnimationView? = null

    //默认的动画
    private val animationJson = "loader.json"

    //头高度dp
    private val LayoutDPH = 60

    init {
        val rootLayout = LinearLayout(context)
        rootLayout.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(LayoutDPH.toFloat()))
        rootLayout.gravity = Gravity.BOTTOM
        rootLayout.orientation = LinearLayout.VERTICAL
        if (mLottieAnimationView == null) {
            mLottieAnimationView = LottieAnimationView(context)
            mLottieAnimationView!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            mLottieAnimationView!!.scaleType = ImageView.ScaleType.CENTER_INSIDE
            mLottieAnimationView!!.repeatCount = -1
            rootLayout.addView(mLottieAnimationView)
        }
        setAnimationViewJson(animationJson)
        this.addView(rootLayout)
    }

    //设置动画
    fun setAnimationViewJson(animationJson: String): LottieHeader {
        if (mLottieAnimationView != null && animationJson.isNotBlank()) {
            mLottieAnimationView!!.setAnimation(animationJson)
        }
        return this
    }

    //设置动画速度
    fun setDuration(duration: Long): LottieHeader {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f)
            animator!!.repeatCount = ValueAnimator.INFINITE
            animator!!.interpolator = LinearInterpolator()
            //取消原本动画的效果
            mLottieAnimationView!!.cancelAnimation()
            animator!!.addUpdateListener { animation ->
                mLottieAnimationView!!.progress = animation.animatedValue as Float
            }
        }
        animator!!.duration = duration
        return this
    }


    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        //如果是手拖动则设置动画进度
        if (mLottieAnimationView != null) {
            //设置动画大小尺寸（如果偏移大于高度则使用最大高度），为了使动画中心点始终位于显示区域的中心
            val hpx = if (offset > height) height else offset
            mLottieAnimationView!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, hpx)
            if (isDragging) {
                val progress = if (offset.toFloat() / height > 1f) 1f else offset.toFloat() / height
                //  mLottieAnimationView.setScale(progress / 20);
                mLottieAnimationView!!.progress = progress
                // LogUtils.i("onMoving:offset=" + offset + " height=" + height);
            }
        }

    }


    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight)
        if (animator != null) {
            animator!!.start()
        } else if (mLottieAnimationView != null) {
            mLottieAnimationView!!.playAnimation()
        }

    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        if (animator != null) {
            animator!!.cancel()
        }
        if (mLottieAnimationView != null) {
            mLottieAnimationView!!.cancelAnimation()
        }
        return super.onFinish(refreshLayout, success)
    }


}
