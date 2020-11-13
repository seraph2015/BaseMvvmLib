package org.seraph.module_image_search

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.hilt.android.qualifiers.ApplicationContext
import org.seraph.lib_comm.BaseAppLike
import org.seraph.module_image_search.data.db.ModuleSearchImageDatabase
import javax.inject.Inject

/**
 * app初始化
 **/
class SearchImageAppLike @Inject constructor(@ApplicationContext context: Context) :
    BaseAppLike(context as Application) {

    override fun onCreate() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (SearchImageConstants.DEBUG) {
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
                    DatabaseConfig.builder(ModuleSearchImageDatabase::class.java)
                        .databaseName(SearchImageConstants.DB_NAME) //设置数据库名
                        .build()
                ).build()
        )
    }

}