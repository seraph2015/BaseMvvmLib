package org.seraph.bcy.ui

import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import org.seraph.bcy.AppConstants
import org.seraph.bcy.R
import org.seraph.bcy.databinding.ActBcyShBinding
import org.seraph.bcy.ui.a.BcyShAdapter
import org.seraph.bcy.ui.b.BcyImageBean
import org.seraph.bcy.ui.vm.BcyShVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.utlis.LLayoutManager
import org.seraph.lib.view.NoDataView
import javax.inject.Inject

/**
 * 解析历史
 * date：2019/10/22 11:30
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Route(path = AppConstants.BCY_SEARCH_HISTORY)
class BcyShActivity : ABaseActivity<ActBcyShBinding, BcyShVm>(R.layout.act_bcy_sh) {

    override fun getViewModelClass(): Class<BcyShVm> {
        return BcyShVm::class.java
    }

    @Inject
    lateinit var adapter: BcyShAdapter


    override fun init() {
        initToolbar(binding.tb)
        vm.imageList.observe(this, Observer {
            adapter.onUpdateList(it)
        })
        initView()
        vm.start()
    }

    private fun initView() {
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)

            val bcyImageList =
                GsonUtils.fromJson<BcyImageBean>(item!!.searchKey, BcyImageBean::class.java)
                    .data.multi

            ARouter.getInstance()
                .build(AppConstants.BCY_IMAGE_LIST)
                .withSerializable("list", bcyImageList)
                .navigation()
        }
        adapter.setOnItemLongClickListener { _, _, position ->
            val items = arrayOf<CharSequence>("删除")

            AlertDialog.Builder(this).setItems(items) { _, _ ->
                vm.onDeleteItem(position)
            }.show()


            false
        }
        adapter.setNoDataView(NoDataView(this).setNoDataMsg("暂无历史记录"))
        binding.rv.layoutManager = LLayoutManager(this)
        binding.rv.adapter = adapter
    }


}