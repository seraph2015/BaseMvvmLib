package org.seraph.demo.ui.main

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActivityMainBinding
import org.seraph.demo.ui.main.vm.MainVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.utlis.LLayoutManager
import org.seraph.lib.utlis.SGLayoutManager
import org.seraph.lib.view.NoDataView


@Route(path = AppConstants.PATH_APP_MAIN)
@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding, MainVm>(R.layout.activity_main) {

    override fun bindVM(): MainVm {
        return viewModels<MainVm>().value
    }

    override fun init() {
        binding.vm = vm

        initView()

        //是否输入状态
        vm.showSearch.observe(this, Observer {
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
        vm.searchList.observe(this, Observer { t ->
            vm.searchListAdapter.onUpdateList(t, 1)
        })
        //刷新图片数据
        vm.imageList.observe(this, Observer { t ->
            vm.mAdapter.onUpdateList(t)
        })
        //搜索的文字内容
        vm.inputStr.observe(this, Observer { t ->
            //如果不是null则显示开始搜索按钮
            vm.showStartSearch.set(t.isNotEmpty())
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
        binding.rvImage.adapter = vm.mAdapter

        vm.searchListAdapter.setNoDataView(
            NoDataView(
                this,
                NoDataView.NO_DATE
            ).setNoDataMsg("暂无搜索记录！")
        )
        vm.searchListAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position -> vm.onSearchItemClick(position) }
        vm.mAdapter.setNoDataView(
            NoDataView(this, NoDataView.NO_DATE)
                .setNoDataMsg("快来度娘一下你喜欢图片吧！")
                .setNoDateIsListener(false)
                .setOnClickListener(object : NoDataView.OnNoDataClickListener {
                    override fun onClick() {
                        vm.getOnePage()
                    }
                })
        )
        vm.mAdapter.setOnItemClickListener { _, _, position -> vm.onStartImagePreview(position) }

        vm.mAdapter.setOnLoadMoreListener({
            vm.getNextPage()
        }, binding.rvImage)

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


}
