package org.seraph.demo.ui.welcome.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.util.SPUtils
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.lib.ui.base.ABaseViewModel

/**
 * org.seraph.ktmvvm.ui.welcome
 * date：2019/4/24 09:42
 * author：xiongj
 * mail：417753393@qq.com
 **/
class GuideVm @ViewModelInject constructor(@Assisted private val savedStateHandle: SavedStateHandle) :
        ABaseViewModel() {

    val images : MutableLiveData<List<Int>>  by lazy {
        MutableLiveData<List<Int>>().also { it.value = arrayListOf(
            R.mipmap.welcome_guide_one,
            R.mipmap.welcome_guide_two,
            R.mipmap.welcome_guide_three,
            R.mipmap.welcome_guide_four
        ) }
    }

    override fun start(vararg any: Any?) {
        SPUtils.getInstance(AppConstants.SP_NAME).put(AppConstants.IS_FIRST, false)
    }


}