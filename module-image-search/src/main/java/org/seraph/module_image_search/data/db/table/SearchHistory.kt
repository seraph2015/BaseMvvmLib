package org.seraph.module_image_search.data.db.table

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import org.seraph.module_image_search.data.db.ModuleSearchImageDatabase

/**
 * 搜索历史
 * date：2019/7/1 14:29
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Table(database = ModuleSearchImageDatabase::class)
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