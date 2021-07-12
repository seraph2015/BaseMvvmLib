package org.seraph.demo.ui.welcome

import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import dagger.hilt.android.AndroidEntryPoint
import org.seraph.demo.LoveConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActWelcomeBinding
import org.seraph.demo.ui.welcome.vm.WelcomeVm
import org.seraph.lib.ui.base.ABaseActivity

/**
 * 欢迎页
 **/
@AndroidEntryPoint
@Route(path = LoveConstants.PATH_LOVE_WELCOME)
class WelcomeActivity :
    ABaseActivity<ActWelcomeBinding, WelcomeVm>(R.layout.act_welcome) {

    override fun bindVM(): WelcomeVm {
        return viewModels<WelcomeVm>().value
    }


    override fun init() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.vm = vm

        binding.btnJump.setOnClickListener { startJump() }

        vm.count.observe(this, Observer { t ->
            if (t <= 0) {
                startJump()
            }
        })
        vm.start()
    }

    /**
     * 跳转
     */
    private fun startJump() {
        ARouter.getInstance().build(LoveConstants.PATH_LOVE_MAIN)
            .withTransition(R.anim.anim_go_top_gone_to_show, R.anim.anim_go_top_show_to_gone)
            .navigation(this)
        finish()
    }


}