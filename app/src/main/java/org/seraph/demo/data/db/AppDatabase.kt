package org.seraph.demo.data.db

import com.raizlabs.android.dbflow.annotation.Database
import org.seraph.demo.AppConfig

/**
 * 数据库（版本）
 * date：2019/7/1 14:26
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Database(version = AppConfig.DB_VERSION)
object AppDatabase