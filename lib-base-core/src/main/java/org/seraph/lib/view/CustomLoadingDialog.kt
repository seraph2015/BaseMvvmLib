package org.seraph.lib.view

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.qualifiers.ActivityContext
import org.seraph.lib.R
import javax.inject.Inject

/**
 * 透明的等待dialog
 */

class CustomLoadingDialog @Inject constructor(@ActivityContext context: Context) :
    Dialog(context, R.style.progress_dialog) {

    private val valueAnimator: ValueAnimator?

    /**
     * 返回是否可以取消
     */
    private var _isBackCancel: Boolean = true

    init {
        setContentView(R.layout.comm_dialog_loading)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lav_loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window?.attributes?.gravity = Gravity.CENTER
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(1200)
        valueAnimator?.let {
            it.repeatCount = ValueAnimator.INFINITE
            it.interpolator = LinearInterpolator()
            it.addUpdateListener { animation ->
                lottieAnimationView.progress = animation.animatedValue as Float
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> if (_isBackCancel) dismiss()
        }
        return true
    }

    override fun show() {
        super.show()
        valueAnimator?.start()
    }

    /**
     * @param assetName 动画文件json路径
     * @param isBackCancel 是否返回可以取消
     */
    fun start(assetName: String? = null, isBackCancel: Boolean = true): CustomLoadingDialog {
        assetName?.let {
            findViewById<LottieAnimationView>(R.id.lav_loading).setAnimation(it)
        }
        _isBackCancel = isBackCancel
        show()
        return this
    }

    override fun dismiss() {
        super.dismiss()
        valueAnimator?.cancel()
    }
}