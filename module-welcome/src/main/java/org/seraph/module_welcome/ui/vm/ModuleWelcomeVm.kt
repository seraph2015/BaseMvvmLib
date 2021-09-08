package org.seraph.module_welcome.ui.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.utlis.copyTextToClip
import org.seraph.module_welcome.WelcomeConstants
import org.seraph.module_welcome.network.repository.WelcomeRepository
import org.seraph.module_welcome.ui.b.WelcomeYiYanBean
import javax.inject.Inject

@HiltViewModel
class ModuleWelcomeVm @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: WelcomeRepository
) :
    ABaseViewModel() {

    private val spUtils: SPUtils = SPUtils.getInstance(WelcomeConstants.SP_NAME)


    val yiYanBean: MutableLiveData<WelcomeYiYanBean> by lazy {
        MutableLiveData<WelcomeYiYanBean>()
    }

    val count: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    override fun start(vararg any: Any?) {
        loadingInfo()
        updateInfo()
        countDown()
    }


    /**
     * 跳转
     */
    fun startJump() {
        if (spUtils.getBoolean(WelcomeConstants.IS_FIRST, true)) {
            ARouter.getInstance().build(WelcomeConstants.PATH_WELCOME_GUIDE).navigation()
        } else {
            ARouter.getInstance().build(WelcomeConstants.PATH_APP_MAIN).navigation()
        }
        onCloseActivity.value = 1
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


    /**
     * 显示信息
     */
    private fun loadingInfo() {
        val beanStr = spUtils.getString(WelcomeConstants.SP_W_INFO, "")
        if (beanStr.isNotBlank()) {
            yiYanBean.value = GsonUtils.fromJson(beanStr, WelcomeYiYanBean::class.java)
        }
    }


    /**
     * 获取信息
     */
    private fun updateInfo() {
        launchOnUI {
            val it = repository.getYiYan()
            spUtils.put(WelcomeConstants.SP_W_INFO, GsonUtils.toJson(it))
        }
    }


    /**
     * 复制到粘贴板
     */
    fun copyYiYan() {
        appContext.copyTextToClip(yiYanBean.value!!.hitokoto!!)
        ToastUtils.showShort("复制文字成功")
    }

}