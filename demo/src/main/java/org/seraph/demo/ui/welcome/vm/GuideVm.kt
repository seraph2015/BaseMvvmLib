package org.seraph.demo.ui.welcome.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.demo.ui.welcome.a.GuidePagerAdapter
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

/**
 * org.seraph.ktmvvm.ui.welcome
 * date：2019/4/24 09:42
 * author：xiongj
 * mail：417753393@qq.com
 **/
class GuideVm @Inject constructor(var guidePagerAdapter: GuidePagerAdapter, application: Application) :
        ABaseViewModel(application) {


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