package org.seraph.demo.ui.main

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActivityMainBinding
import org.seraph.demo.ui.main.a.BaseLoadStateAdapter
import org.seraph.demo.ui.main.a.ImageBdAdapter
import org.seraph.demo.ui.main.vm.MainVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewActivity
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewBean
import org.seraph.lib.utlis.LLayoutManager
import org.seraph.lib.utlis.SGLayoutManager
import org.seraph.lib.view.NoDataView
import java.util.*
import javax.inject.Inject


@Route(path = AppConstants.PATH_APP_MAIN)
@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding, MainVm>(R.layout.activity_main) {

    override fun bindVM(): MainVm {
        return viewModels<MainVm>().value
    }

    @Inject
    lateinit var mBdAdapter: ImageBdAdapter

    override fun init() {
        binding.vm = vm

        initView()

        //是否输入状态
        vm.showSearch.observe(this, {
            //图片列表
            binding.rvImage.visibility = if (it) View.GONE else View.VISIBLE
            //输入历史列表
            binding.rvSearch.visibility = if (it) View.VISIBLE else View.GONE
            //设置输入框可聚集
            binding.etSearchInput.isFocusable = it
            //设置触摸聚焦
            binding.etSearchInput.isFocusableInTouchMode = it
            if (it) {
                //请求焦点
                binding.etSearchInput.requestFocus()
                //获取焦点
                binding.etSearchInput.findFocus()
                //请求历史数据
                vm.showSearchHistory()
                //展示软键盘
                KeyboardUtils.showSoftInput(binding.etSearchInput)
            } else {
                //失去关闭键盘
                KeyboardUtils.hideSoftInput(binding.etSearchInput)
            }
        })

        //刷新搜索数据
        vm.searchList.observe(this, { t ->
            vm.searchListAdapter.onUpdateList(t, 1)
        })
        //搜索的文字内容
        vm.inputStr.observe(this, { t ->
            //如果不是null则显示开始搜索按钮
            vm.showStartSearch.set(t.isNotEmpty())
        })
        //清理数据重新开始搜索状态
        vm.cleanList.observe(this, {
            lifecycleScope.launch {
                vm.doSearch().collectLatest { pagingData -> mBdAdapter.submitData(pagingData) }
            }

        })
        vm.start()

    }

    /**
     * 初始化view
     */
    private fun initView() {

        //初始化适配器
        binding.rvSearch.layoutManager = LLayoutManager(this)
        binding.rvSearch.adapter = vm.searchListAdapter

        binding.rvImage.layoutManager = SGLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.rvImage.adapter =  mBdAdapter.withLoadStateHeaderAndFooter(
            BaseLoadStateAdapter(mBdAdapter::retry),
            BaseLoadStateAdapter(mBdAdapter::retry)
        )

        vm.searchListAdapter.setNoDataView(
            NoDataView(
                this,
                NoDataView.NO_DATE
            ).setNoDataMsg("暂无搜索记录！")
        )
        vm.searchListAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position -> vm.onSearchItemClick(position) }

        //软键盘搜索
        binding.etSearchInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    vm.startSearchImage()
                    return true
                }
                return false
            }
        })
    }


//    /**
//     * 跳转开始预览
//     */
//    private fun onStartImagePreview(position: Int) {
//        val photoList = ArrayList<PhotoPreviewBean>()
//        for (baiduImage in mAdapter.data) {
//            val photoPreviewBean = PhotoPreviewBean()
//            photoPreviewBean.objURL = baiduImage.objURL
//            photoPreviewBean.width = baiduImage.width
//            photoPreviewBean.height = baiduImage.height
//            photoList.add(photoPreviewBean)
//        }
//        PhotoPreviewActivity.startPhotoPreview(photoList, position)
//    }


}
