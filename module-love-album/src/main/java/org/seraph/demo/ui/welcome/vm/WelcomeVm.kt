package org.seraph.demo.ui.welcome.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeVm @Inject constructor() : ABaseViewModel() {


    val count: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    override fun start(vararg any: Any?) {
        countDown()
    }

    /**
     * 3秒倒计时转跳
     */
    private fun countDown() {
        launchOnUI {
            var i = 5
            while (isActive && i >= 0) {
                count.value = i
                delay(1000L)
                i -= 1
            }
        }
    }




}