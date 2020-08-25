package org.seraph.lib.view

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import org.seraph.lib.R
import javax.inject.Inject

/**
 * 透明的等待dialog
 */

class CustomLoadingDialog @Inject constructor(@ActivityContext context: Context) : Dialog(context, R.style.progress_dialog) {

    private val valueAnimator: ValueAnimator?

    init {
        setContentView(R.layout.comm_dialog_loading)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lav_loading)
        setCanceledOnTouchOutside(true)
        window!!.attributes.gravity = Gravity.CENTER
        setCancelable(false)
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(1200)
        valueAnimator!!.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation -> lottieAnimationView.progress = animation.animatedValue as Float }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> dismiss()
        }
        return true
    }

    override fun show() {
        super.show()
        valueAnimator?.start()
    }

    fun start(): CustomLoadingDialog {
        show()
        return this
    }

    override fun dismiss() {
        super.dismiss()
        valueAnimator?.cancel()
    }
}