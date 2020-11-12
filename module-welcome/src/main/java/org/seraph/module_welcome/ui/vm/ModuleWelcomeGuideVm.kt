package org.seraph.module_welcome.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.module_welcome.R
import org.seraph.module_welcome.WelcomeConstants

class ModuleWelcomeGuideVm @ViewModelInject constructor() :
        ABaseViewModel() {

    val images : MutableLiveData<List<Int>>  by lazy {
        MutableLiveData<List<Int>>().also { it.value = arrayListOf(
            R.mipmap.module_welcome_guide_one,
            R.mipmap.module_welcome_guide_two,
            R.mipmap.module_welcome_guide_three,
            R.mipmap.module_welcome_guide_four
        ) }
    }

    override fun start(vararg any: Any?) {
        SPUtils.getInstance(WelcomeConstants.SP_NAME).put(WelcomeConstants.IS_FIRST, false)
    }


}