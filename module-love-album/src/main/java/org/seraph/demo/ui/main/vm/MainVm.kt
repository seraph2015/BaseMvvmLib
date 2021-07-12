package org.seraph.demo.ui.main.vm

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

/**
 * 主页
 **/
@HiltViewModel
class MainVm @Inject constructor() : ABaseViewModel() {

    override fun start(vararg any: Any?) {

    }

    /**
     * 判断是否已经点击过一次回退键
     */
    private var isBackPressed = false

    fun doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true
            ToastUtils.showShort("再按一次退出程序")
        } else {
            ActivityUtils.finishAllActivities()
        }
        launchOnUI {
            delay(2000L)
            isBackPressed = false
        }
    }


}

