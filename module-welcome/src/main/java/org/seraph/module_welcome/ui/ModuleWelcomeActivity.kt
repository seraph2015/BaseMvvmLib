package org.seraph.module_welcome.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.module_welcome.R
import org.seraph.module_welcome.databinding.ModuleWelcomeActWelcomeBinding
import org.seraph.module_welcome.ui.vm.ModuleWelcomeVm

/**
 * 欢迎页
 * date：2019/4/23 11:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
@AndroidEntryPoint
class ModuleWelcomeActivity :
    ABaseActivity<ModuleWelcomeActWelcomeBinding, ModuleWelcomeVm>(R.layout.module_welcome_act_welcome) {

    override fun bindVM(): ModuleWelcomeVm {
        return viewModels<ModuleWelcomeVm>().value
    }

    override fun init() {
        binding.vm = vm

        vm.count.observe(this, Observer { t ->
            if (t <= 0) {
                vm.startJump()
            }
        })
        vm.start()
    }


}