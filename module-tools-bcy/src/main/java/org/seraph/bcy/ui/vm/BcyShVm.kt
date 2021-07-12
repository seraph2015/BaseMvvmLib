package org.seraph.bcy.ui.vm

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.seraph.bcy.data.repository.DBRepository
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib_comm.db.table.SearchHistory
import javax.inject.Inject

@HiltViewModel
class BcyShVm @Inject constructor(
    private val dbRepository: DBRepository
) : ABaseViewModel() {


    /**
     * 获取到的图片列表
     */
    val imageList: MutableLiveData<List<SearchHistory>> by lazy {
        MutableLiveData<List<SearchHistory>>()
    }
    /**
     * 时间排序方式
     */
    private var ascending: Boolean = false

    override fun start(vararg any: Any?) {
        startSearchHistory()
    }
    private fun startSearchHistory() {
        launchOnUI({
            imageList.value = dbRepository.getSearchBcyList(ascending)
        }, {

        })

    }

    fun onDeleteItem(position: Int) {
        dbRepository.deleteSearchBcyDB(imageList.value!![position].searchKey!!)
        //刷新
        startSearchHistory()
    }

    fun onTimeDx() {
        ascending = false
        start()
    }

    fun onTimeZx() {
        ascending = true
        start()
    }



}
