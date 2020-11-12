package org.seraph.module_welcome

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp

/**
 * app初始化
 * date：2019/4/18 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
@HiltAndroidApp
class WelcomeApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        //初始化第三方sdk
        initSDK()
    }

    private fun initSDK() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (WelcomeConstants.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        //阿里云路由框架
        ARouter.init(this)
        Utils.init(this)

    }


}