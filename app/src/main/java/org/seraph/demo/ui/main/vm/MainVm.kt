package org.seraph.demo.ui.main.vm

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import org.seraph.demo.AppConfig
import org.seraph.demo.R
import org.seraph.demo.data.repository.DBRepository
import org.seraph.demo.data.repository.OtherRepository
import org.seraph.demo.ui.main.a.ImageListBaiduAdapter
import org.seraph.demo.ui.main.a.SearchListAdapter
import org.seraph.demo.ui.main.b.ImageBaiduBean
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewActivity
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewBean
import java.util.*
import javax.inject.Inject

/**
 * 主页
 * date：2019/5/23 11:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
class MainVm @Inject constructor(
    application: Application,
    private var otherRepository: OtherRepository,
    private var dbRepository: DBRepository,
    var mAdapter: ImageListBaiduAdapter,
    var searchListAdapter: SearchListAdapter
) :
    ABaseViewModel(application) {

    /**
     * 获取到的图片列表
     */
    val imageList: MutableLiveData<List<ImageBaiduBean.BaiduImage>> by lazy {
        MutableLiveData<List<ImageBaiduBean.BaiduImage>>()
    }


    /**
     * 搜索列表
     */
    val searchList: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    /**
     * 是否输入状态
     */
    val showSearch: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    /**
     * 是否显示开始搜索按钮（根据是否有文字）
     */
    val showStartSearch = ObservableField<Boolean>()


    /**
     * 输入需要搜索的内容
     */
    val inputStr: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    override fun start() {
        //设置为非输入状态
        showSearch.value = false
    }


    /**
     * 点击事件
     */
    fun onClick(v: View) {
        when (v.id) {

            R.id.iv_clear_input -> inputStr.value = ""//清理输入

            R.id.tv_cancel -> showSearch.value = false//取消当前的输入状态

            R.id.iv_search -> startSearchImage()  //开始搜索

            R.id.et_search_input -> showSearch.value = true  //设置输入状态
        }
    }


    /**
     * 跳转开始预览
     */
    fun onStartImagePreview(position: Int) {
        val photoList = ArrayList<PhotoPreviewBean>()
        for (baiduImage in mAdapter.data) {
            val photoPreviewBean = PhotoPreviewBean()
            photoPreviewBean.objURL = baiduImage.objURL
            photoPreviewBean.width = baiduImage.width
            photoPreviewBean.height = baiduImage.height
            photoList.add(photoPreviewBean)
        }
        PhotoPreviewActivity.startPhotoPreview(photoList, position)
    }


    /**
     * 搜索列表点击
     */
    fun onSearchItemClick(position: Int) {
        if (position == searchList.value!!.size - 1) {
            dbRepository.deleteBaiduAllSearchDB()
            //刷新界面
            showSearchHistory()
        } else {
            //设置搜索文字，开始搜索
            inputStr.value = searchList.value!![position]
            startSearchImage()
        }
    }

    /**
     * 搜索数据库
     */
    fun showSearchHistory() {
        //查询本地数据搜索历史（时间倒叙）
        launchOnUI {
            val searchDBList = dbRepository.getBaiduSearch()
            if (searchDBList.isNotEmpty()) {
                searchDBList.add("清除历史记录")
            }
            searchList.value = searchDBList
        }
    }


    /**
     * 开始搜索图片
     */
    fun startSearchImage() {
        if (inputStr.value.isNullOrBlank()) {
            ToastUtils.showShort("请输入需要搜索的图片关键字")
            return
        }
        dbRepository.saveBaiduSearchToDB(inputStr.value!!)

        //关闭输入状态
        showSearch.value = false

        mAdapter.setNewData(null)
        //设置开始
        mAdapter.getNoDataView()?.setLoading()

        getOnePage()
    }


    /**
     * 获取数据
     */
    private fun doSearch(pageNo: Int) {
        launchOnUI({
            imageList.value = otherRepository.doSearch(pageNo, AppConfig.PAGE_SIZE, inputStr.value!!)
        }, {
            imageList.value = null
        })
    }

    fun getOnePage() {
        doSearch(mAdapter.getOnePage())
    }

    fun getNextPage() {
        doSearch(mAdapter.getNextPage())
    }

}

