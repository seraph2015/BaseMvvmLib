package org.seraph.module_image_search.ui.vm

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import org.seraph.lib.ui.base.ABaseViewModel
import org.seraph.module_image_preview.ui.ImagePreviewActivity
import org.seraph.module_image_preview.ui.ImagePreviewBean
import org.seraph.module_image_search.R
import org.seraph.module_image_search.SearchImageConstants
import org.seraph.module_image_search.data.repository.DBRepository
import org.seraph.module_image_search.data.repository.OtherRepository
import org.seraph.module_image_search.ui.a.ImageListBaiduAdapter
import org.seraph.module_image_search.ui.a.SearchListAdapter
import org.seraph.module_image_search.ui.b.BaiduImage

/**
 * 主页
 * date：2019/5/23 11:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ImageSearchVm @ViewModelInject constructor(
    private var otherRepository: OtherRepository,
    private var dbRepository: DBRepository,
    var mAdapter: ImageListBaiduAdapter,
    var searchListAdapter: SearchListAdapter
) :
    ABaseViewModel() {


    /**
     * 获取到的图片列表
     */
    val imageList: MutableLiveData<List<BaiduImage>> by lazy {
        MutableLiveData<List<BaiduImage>>()
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

    override fun start(vararg any: Any?) {
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
        val photoList = ArrayList<ImagePreviewBean>()
        for (baiduImage in mAdapter.data) {
            val photoPreviewBean = ImagePreviewBean()
            photoPreviewBean.objURL = baiduImage.objURL
            photoPreviewBean.width = baiduImage.width
            photoPreviewBean.height = baiduImage.height
            photoList.add(photoPreviewBean)
        }
        ImagePreviewActivity.startPhotoPreview(photoList, position)
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
            imageList.value =
                otherRepository.doSearch(pageNo, SearchImageConstants.PAGE_SIZE, inputStr.value!!)
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

