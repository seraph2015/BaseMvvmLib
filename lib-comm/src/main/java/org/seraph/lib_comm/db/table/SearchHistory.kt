package org.seraph.lib_comm.db.table

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import org.seraph.lib_comm.db.AppDatabase

/**
 * 搜索历史
 **/
@Table(database = AppDatabase::class)
data class SearchHistory(
    /**
     * 主键
     */
    @PrimaryKey(autoincrement = true)
    var _id: Long? = null,
    /**
     * 所属的用户id
     */
    @Column
    var userId: Int = 0,
    /**
     * 搜索的类型标签(区分不同的地方)
     */
    @Column
    var type: String? = null,
    /**
     * 搜索的键约束唯一
     */
    @Column
    var searchKey: String? = null,
    /**
     * 搜索时间
     */
    @Column
    var searchTime: Long = 0
)