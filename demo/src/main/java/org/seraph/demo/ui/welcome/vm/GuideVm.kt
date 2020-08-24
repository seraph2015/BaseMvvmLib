package org.seraph.demo.ui.welcome.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.util.SPUtils
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.demo.ui.welcome.a.GuidePagerAdapter
import org.seraph.lib.ui.base.ABaseViewModel

/**
 * org.seraph.ktmvvm.ui.welcome
 * date：2019/4/24 09:42
 * author：xiongj
 * mail：417753393@qq.com
 **/
class GuideVm @ViewModelInject constructor(var guidePagerAdapter: GuidePagerAdapter, @Assisted private val savedStateHandle: SavedStateHandle) :
        ABaseViewModel() {


    val images: MutableLiveData<List<Int>> by lazy {
        MutableLiveData<List<Int>>()
    }


    override fun start() {
        SPUtils.getInstance(AppConstants.SP_NAME).put(AppConstants.IS_FIRST, false)
        images.value = arrayListOf(
                R.mipmap.welcome_guide_one,
                R.mipmap.welcome_guide_two,
                R.mipmap.welcome_guide_three,
                R.mipmap.welcome_guide_four
        )
    }


}