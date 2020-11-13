package org.seraph.module_welcome

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import org.seraph.lib_comm.BaseAppLike
import javax.inject.Inject

/**
 * app初始化
 **/
class WelcomeAppLike @Inject constructor(@ApplicationContext context: Context) :
    BaseAppLike(context as Application) {

    override fun onCreate() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (WelcomeConstants.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        //阿里云路由框架
        ARouter.init(mContext)
        Utils.init(mContext)
    }

}