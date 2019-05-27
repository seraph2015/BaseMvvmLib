package org.seraph.demo.data.repository

import org.seraph.demo.AppConstants
import org.seraph.demo.data.db.help.SearchHistoryHelp
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
class DBRepository @Inject constructor(
    private var searchHistoryHelp: SearchHistoryHelp
) : ABaseRepository() {


    /**
     * 获取百度搜索历史
     */
    suspend fun getBaiduSearch(): ArrayList<String> {
        return apiIoCall {
            val tempList = ArrayList<String>()
            val searchList = searchHistoryHelp.querySearchDB(-1, AppConstants.DB_TYPE_IMG_BAIDU)
            for (table in searchList) {
                tempList.add(table.searchKey)
            }
            return@apiIoCall tempList
        }
    }

    /**
     * 保存到数据库
     */
    fun saveBaiduSearchToDB(keyWordStr: String) {
        searchHistoryHelp.saveSearchToDB(-1, AppConstants.DB_TYPE_IMG_BAIDU, keyWordStr)

    }

    /**
     * 删除百度数据库历史
     */
    fun deleteBaiduAllSearchDB() {
        searchHistoryHelp.deleteAllSearchDB(-1, AppConstants.DB_TYPE_IMG_BAIDU)
    }


}