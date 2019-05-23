package org.seraph.demo.ui.welcome

import androidx.lifecycle.Observer
import org.seraph.demo.R
import org.seraph.demo.ui.welcome.vm.WelcomeVm
import org.seraph.lib.ui.base.ABaseActivity

/**
 * 欢迎页
 * date：2019/4/23 11:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeActivity :
        ABaseActivity<org.seraph.demo.databinding.ActWelcomeBinding, WelcomeVm>(R.layout.act_welcome) {


    override fun getViewModelClass(): Class<WelcomeVm> {
        return WelcomeVm::class.java
    }


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