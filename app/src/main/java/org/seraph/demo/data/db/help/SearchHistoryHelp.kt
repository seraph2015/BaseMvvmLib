package org.seraph.demo.data.db.help

import org.seraph.demo.data.db.gen.DaoSession
import org.seraph.demo.data.db.gen.SearchHistoryTableDao
import org.seraph.demo.data.db.table.SearchHistoryTable
import javax.inject.Inject

/**
 * 通用的用户记录表
 * date：2017/7/26 17:01
 * author：xiongj
 * mail：417753393@qq.com
 */
class SearchHistoryHelp @Inject constructor(daoSession: DaoSession) {


    private val mSearchHistoryTableDao: SearchHistoryTableDao = daoSession.searchHistoryTableDao

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

        val searchHistoryTable = SearchHistoryTable()
        searchHistoryTable.searchKey = saveStr
        searchHistoryTable.searchTime = System.currentTimeMillis()
        searchHistoryTable.type = type
        searchHistoryTable.userId = userId
        mSearchHistoryTableDao.save(searchHistoryTable)
    }

    /**
     * 清理对应用户对应类型的所有历史数据库
     */
    fun deleteAllSearchDB(userId: Int, type: String) {
        val historyTableList = mSearchHistoryTableDao.queryBuilder()
                .where(SearchHistoryTableDao.Properties.UserId.eq(userId), SearchHistoryTableDao.Properties.Type.eq(type))
                .list()
        for (searchHistoryTable in historyTableList) {
            mSearchHistoryTableDao.delete(searchHistoryTable)
        }
    }

    /**
     * 清理对应用户对应类型的单个历史数据库
     * 在保存的时候清理了对应重复保存的key，所有使用key进行唯一性移除数据
     *
     * @param userId    用户id
     * @param type      记录的类型（区分不同的使用地方）
     * @param deleteStr 需要移除的对应key
     */
    fun deleteAllSearchDB(userId: Int, type: String, deleteStr: String) {
        val historyTableList = mSearchHistoryTableDao.queryBuilder()
                .where(
                        SearchHistoryTableDao.Properties.UserId.eq(userId),
                        SearchHistoryTableDao.Properties.Type.eq(type),
                        SearchHistoryTableDao.Properties.SearchKey.eq(deleteStr)
                )
                .list()
        for (searchHistoryTable in historyTableList) {
            mSearchHistoryTableDao.delete(searchHistoryTable)
        }
    }


    /**
     * 查询对应用户的和对应区域的历史记录信息（时间倒叙）
     *
     * @param userId 用户id
     * @param type   记录的类型（区分不同的使用地方）
     */
    fun querySearchDB(userId: Int, type: String): List<SearchHistoryTable> {
        return mSearchHistoryTableDao.queryBuilder()
                .where(SearchHistoryTableDao.Properties.UserId.eq(userId), SearchHistoryTableDao.Properties.Type.eq(type))
                .orderDesc(SearchHistoryTableDao.Properties.SearchTime).list()
    }

}
