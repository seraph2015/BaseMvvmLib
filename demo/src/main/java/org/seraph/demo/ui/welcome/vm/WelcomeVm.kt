package org.seraph.demo.ui.welcome.vm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.seraph.demo.AppConstants
import org.seraph.demo.data.repository.OtherRepository
import org.seraph.demo.ui.welcome.WelcomeActivity
import org.seraph.demo.ui.welcome.b.YiYanBean
import org.seraph.lib.ui.base.ABaseViewModel

/**
 * org.seraph.ktmvvm.ui.welcome
 * date：2019/4/23 11:14
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeVm @ViewModelInject constructor(
    @ApplicationContext private val appContext: Context,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val yiyanRepository: OtherRepository
) :
    ABaseViewModel() {

    private val spUtils: SPUtils = SPUtils.getInstance(AppConstants.SP_NAME)


    val yiYanBean: MutableLiveData<YiYanBean> by lazy {
        MutableLiveData<YiYanBean>()
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
        if (spUtils.getBoolean(AppConstants.IS_FIRST, true)) {
            ARouter.getInstance().build(AppConstants.PATH_WELCOME_GUIDE).navigation()
        } else {
            ARouter.getInstance().build(AppConstants.PATH_APP_MAIN).navigation()
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
        val beanStr = spUtils.getString(AppConstants.SP_W_INFO, "")
        if (beanStr.isNotBlank()) {
            yiYanBean.value = GsonUtils.fromJson(beanStr, YiYanBean::class.java)
        }
    }


    /**
     * 获取信息
     */
    private fun updateInfo() {
        launchOnUI {
            val it = yiyanRepository.getYiYan()
            spUtils.put(AppConstants.SP_W_INFO, GsonUtils.toJson(it))
        }
    }


    /**
     * 复制到粘贴板
     */
    fun copyYiYan() {
        val clipboardManager: ClipboardManager =
            appContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("text", yiYanBean.value?.hitokoto)
        clipboardManager.primaryClip = clipData
        ToastUtils.showShort("复制文字成功")
    }

}