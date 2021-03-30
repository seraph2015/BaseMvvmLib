package org.seraph.lib_comm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import org.seraph.lib_comm.db.AppDatabase

/**
 * 一些通用的初始化
 */
abstract class BaseAppLike(val mContext: Application){

    /**
     * 一些公用的初始化
     */
    fun onCreate(isDebug: Boolean = false) {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (isDebug) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        //阿里云路由框架
        ARouter.init(mContext)
        Utils.init(mContext)
        //初始化dbflow
        FlowManager.init(
            FlowConfig.builder(mContext)
                .addDatabaseConfig(
                    DatabaseConfig.builder(AppDatabase::class.java)
                        .databaseName(LibCommConstants.DB_NAME) //设置数据库名
                        .build()
                ).build()
        )
    }
}