package org.seraph.demo.di.module

import dagger.Module
import dagger.Provides
import org.seraph.demo.data.db.DBHelper
import org.seraph.demo.data.db.gen.DaoSession
import javax.inject.Singleton

/**
 * app全局单例注册
 * date：2019/4/18 15:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideDaoSession(dbHelper: DBHelper): DaoSession {
        return dbHelper.daoSession
    }

}