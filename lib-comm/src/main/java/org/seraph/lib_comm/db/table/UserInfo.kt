package org.seraph.lib_comm.db.table

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import org.seraph.lib_comm.db.AppDatabase

/**
 * 用户信息
 **/
@Table(database = AppDatabase::class)
data class UserInfo(
    /**
     * 主键
     */
    @PrimaryKey(autoincrement = true)
    var _id: Long? = null,

    @Column
    var userId: String? = null,

    @Column
    var nick: String? = null,

    @Column
    var phone: String? = null,

    @Column
    var avatarUrl: String? = null,

    @Column
    var token: String? = null,

    @Column
    var password: String? = null
)