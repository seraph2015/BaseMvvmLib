package org.seraph.bcy.ui.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.seraph.bcy.data.db.table.SearchHistory
import org.seraph.bcy.data.repository.DBRepository
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject


class BcyShVm @Inject constructor(
    application: Application,
    private val dbRepository: DBRepository
) : ABaseViewModel(application) {


    /**
     * 获取到的图片列表
     */
    val imageList: MutableLiveData<List<SearchHistory>> by lazy {
        MutableLiveData<List<SearchHistory>>()
    }


    override fun start() {
        startSearchHistory()
    }

    private fun startSearchHistory() {
        launchOnUI({
            imageList.value = dbRepository.getSearchBcyList()
        }, {

        })

    }

    fun onDeleteItem(position: Int) {
        dbRepository.deleteSearchBcyDB(imageList.value!![position].searchKey!!)
        //刷新
        startSearchHistory()
    }

}
