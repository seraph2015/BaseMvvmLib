package org.seraph.bcy.data.db.help

import com.raizlabs.android.dbflow.kotlinextensions.*
import org.seraph.bcy.data.db.table.SearchHistory
import org.seraph.bcy.data.db.table.SearchHistory_Table

/**
 * org.seraph.demo.data.db.help
 * date：2019/7/1 16:16
 * author：xiongj
 * mail：417753393@qq.com
 **/
object SHHelp {

    /**
     * 清理对应用户对应类型的单个历史数据库
     * 在保存的时候清理了对应重复保存的key，所有使用key进行唯一性移除数据
     *
     * @param userId    用户id
     * @param type      记录的类型（区分不同的使用地方）
     * @param deleteStr 需要移除的对应key
     */
    fun deleteAllSearchDB(userId: Int, type: String, deleteStr: String? = null) {
        val w = delete<SearchHistory>().where(
            SearchHistory_Table.userId.eq(userId),
            SearchHistory_Table.type.eq(type)
        )
        deleteStr?.let {
            w.and(SearchHistory_Table.searchKey.eq(deleteStr))
        }
        w.execute()
    }


    /**
     * 保存到数据库
     *
     * @param userId  区分不同用户的id
     * @param type    区分不同地方使用的type
     * @param saveStr 保存的str
     */
    fun saveSearchToDB(userId: Int, type: String, saveStr: String) {
        //清理之前在同一用户种同一类型的重复的key
        deleteAllSearchDB(userId, type, saveStr)
        //保存到数据库
        SearchHistory(
            searchKey = saveStr,
            searchTime = System.currentTimeMillis(),
            type = type,
            userId = userId
        ).save()
    }


    /**
     * 查询对应用户的和对应区域的历史记录信息（时间倒叙）
     *
     * @param userId 用户id
     * @param type   记录的类型（区分不同的使用地方）
     */
    fun querySearchDB(userId: Int, type: String, ascending: Boolean = false): List<SearchHistory> {
        return (
                select from SearchHistory::class
                        where (SearchHistory_Table.userId.eq(userId)).and(
                    SearchHistory_Table.type.eq(
                        type
                    )
                )
                )
            .orderBy(SearchHistory_Table.searchTime, ascending)
            .queryList()
    }


}