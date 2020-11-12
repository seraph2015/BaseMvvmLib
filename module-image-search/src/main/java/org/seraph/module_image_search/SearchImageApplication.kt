package org.seraph.module_image_search

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.hilt.android.HiltAndroidApp
import org.seraph.module_image_search.data.db.ModuleSearchImageDatabase

/**
 * app初始化
 * date：2019/4/18 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
@HiltAndroidApp
class SearchImageApplication : Application() {

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
        if (SearchImageConstants.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        //阿里云路由框架
        ARouter.init(this)
        Utils.init(this)
        //初始化dbflow
        FlowManager.init(
            FlowConfig.builder(this)
                .addDatabaseConfig(
                    DatabaseConfig.builder(ModuleSearchImageDatabase::class.java)
                        .databaseName(SearchImageConstants.DB_NAME) //设置数据库名
                        .build()
                ).build()
        )
    }


}