package org.seraph.demo.data.db

import android.app.Application
import android.content.Context
import com.github.yuweiguocn.library.greendao.MigrationHelper
import org.greenrobot.greendao.database.Database
import org.seraph.demo.AppConfig
import org.seraph.demo.data.db.gen.DaoMaster
import org.seraph.demo.data.db.gen.DaoSession
import org.seraph.demo.data.db.gen.SearchHistoryTableDao
import javax.inject.Inject

/**
 * 数据库帮助类
 * date：2017/9/18 11:24
 * author：Seraph
 * mail：417753393@qq.com
 */
class DBHelper constructor(context: Context, name: String) : DaoMaster.OpenHelper(context, name) {

    @Inject
    constructor(context: Application) : this(context.applicationContext, AppConfig.DB_NAME)


    override fun onUpgrade(db: Database, oldVersion: Int, newVersion: Int) {
        //数据库升级
        MigrationHelper.migrate(db, object : MigrationHelper.ReCreateAllTableListener {
            override fun onCreateAllTables(db: Database, ifNotExists: Boolean) {
                DaoMaster.createAllTables(db, ifNotExists)
            }

            override fun onDropAllTables(db: Database, ifExists: Boolean) {
                DaoMaster.dropAllTables(db, ifExists)
            }
        }, SearchHistoryTableDao::class.java)
    }

    val daoSession: DaoSession
        get() {
            //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            return DaoMaster(this.writableDatabase).newSession()
        }

}
