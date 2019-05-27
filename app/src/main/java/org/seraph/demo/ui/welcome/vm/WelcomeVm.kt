package org.seraph.demo.ui.welcome.vm

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.seraph.demo.AppConstants
import org.seraph.demo.data.repository.OtherRepository
import org.seraph.demo.ui.welcome.WelcomeActivity
import org.seraph.demo.ui.welcome.b.YiYanBean
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

/**
 * org.seraph.ktmvvm.ui.welcome
 * date：2019/4/23 11:14
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeVm @Inject constructor(
    application: Application,
    private val yiyanRepository: OtherRepository,
    private val welcomeActivity: WelcomeActivity
) :
    ABaseViewModel(application) {

    private val spUtils: SPUtils = SPUtils.getInstance(AppConstants.SP_NAME)


    val yiYanBean: MutableLiveData<YiYanBean> by lazy {
        MutableLiveData<YiYanBean>()
    }

    val count: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    override fun start() {
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
        welcomeActivity.finish()
    }

    /**
     * 3秒倒计时转跳
     */
    private fun countDown() {
        launchOnUI {
            withContext(Dispatchers.IO) {
                var i = 5
                while (isActive) {
                    count.postValue(i)
                    delay(1000L)
                    i -= 1
                }
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
            getApplication<Application>().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("text", yiYanBean.value?.hitokoto)
        clipboardManager.primaryClip = clipData
        ToastUtils.showShort("复制文字成功")
    }

}