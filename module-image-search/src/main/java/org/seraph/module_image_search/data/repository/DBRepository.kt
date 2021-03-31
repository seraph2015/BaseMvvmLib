package org.seraph.module_image_search.data.repository

import org.seraph.module_image_search.SearchImageConstants
import org.seraph.lib.ui.base.ABaseRepository
import org.seraph.lib_comm.db.help.SHHelp
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
    suspend fun getBaiduSearch(): ArrayList<String> {
        return apiIoCall {
            val tempList = ArrayList<String>()
            val searchList = SHHelp.querySearchDB(-1, SearchImageConstants.DB_TYPE_IMG_BAIDU)
            for (table in searchList) {
                tempList.add(table.searchKey!!)
            }
            return@apiIoCall tempList
        }
    }

    /**
     * 保存到数据库
     */
    fun saveBaiduSearchToDB(keyWordStr: String) {
        SHHelp.saveSearchToDB(-1, SearchImageConstants.DB_TYPE_IMG_BAIDU, keyWordStr)
    }

    /**
     * 删除百度数据库历史
     */
    fun deleteBaiduAllSearchDB() {
        SHHelp.deleteAllSearchDB(-1, SearchImageConstants.DB_TYPE_IMG_BAIDU)
    }


}