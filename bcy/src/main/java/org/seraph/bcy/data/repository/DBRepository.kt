package org.seraph.bcy.data.repository

import org.seraph.bcy.AppConstants
import org.seraph.bcy.data.db.help.SHHelp
import org.seraph.bcy.data.db.table.SearchHistory
import org.seraph.lib.ui.base.ABaseRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * db数据
 * date：2019/4/28 17:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
class DBRepository @Inject constructor() : ABaseRepository() {


    /**
     * 获取搜索历史
     */
    suspend fun getSearchBcyList(ascending: Boolean = false): List<SearchHistory> {
        return apiIoCall {
            return@apiIoCall SHHelp.querySearchDB(-1, AppConstants.DB_TYPE_IMG_BCY, ascending)
        }
    }

    /**
     * 保存到数据库
     */
    fun saveSearchBcyToDB(keyWordStr: String) {
        SHHelp.saveSearchToDB(-1, AppConstants.DB_TYPE_IMG_BCY, keyWordStr)
    }

    /**
     * 删除数据库历史
     */
    fun deleteSearchBcyDB(deleteStr: String) {
        SHHelp.deleteAllSearchDB(-1, AppConstants.DB_TYPE_IMG_BCY, deleteStr)
    }

    /**
     * 删除数据库历史
     */
    fun deleteAllSearchBcyDB() {
        SHHelp.deleteAllSearchDB(-1, AppConstants.DB_TYPE_IMG_BCY)
    }


}