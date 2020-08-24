package org.seraph.demo.ui.welcome

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.seraph.demo.R
import org.seraph.demo.databinding.ActWelcomeBinding
import org.seraph.demo.ui.welcome.vm.WelcomeVm
import org.seraph.lib.ui.base.ABaseActivity

/**
 * 欢迎页
 * date：2019/4/23 11:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
@AndroidEntryPoint
class WelcomeActivity :
    ABaseActivity<ActWelcomeBinding>(R.layout.act_welcome) {

    private val vm by viewModels<WelcomeVm>()

    override fun init() {
        binding.vm = vm

        vm.count.observe(this, Observer { t ->
            if (t <= 0) {
                vm.startJump()
                finish()
            }
        })
        vm.start()
    }


}