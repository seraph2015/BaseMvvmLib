package org.seraph.bcy.ui

import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import dagger.hilt.android.AndroidEntryPoint
import org.seraph.bcy.BcyConstants
import org.seraph.bcy.R
import org.seraph.bcy.databinding.ActBcyIlBinding
import org.seraph.bcy.ui.a.BcyILAdapter
import org.seraph.bcy.ui.b.MultiBean
import org.seraph.bcy.ui.vm.BcyILVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.utlis.SGLayoutManager
import org.seraph.lib.view.NoDataView
import org.seraph.module_image_preview.ui.ImagePreviewActivity
import org.seraph.module_image_preview.ui.ImagePreviewBean
import javax.inject.Inject

/**
 * 解析图片列表
 **/
@Route(path = BcyConstants.BCY_IMAGE_LIST)
@AndroidEntryPoint
class BcyILActivity : ABaseActivity<ActBcyIlBinding, BcyILVm>(R.layout.act_bcy_il) {

    override fun bindVM(): BcyILVm {
        return viewModels<BcyILVm>().value
    }


    @Autowired
    lateinit var list: ArrayList<MultiBean>

    @Inject
    lateinit var adapter: BcyILAdapter

    private val sm = SGLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


    override fun init() {
        val tb = initToolbar(binding.tb)
        tb.title = "图片列表（${list.size}张）"
        tb.setOnMenuItemClickListener {
            if (sm.spanCount == 1) {
                sm.spanCount = 2
            } else {
                sm.spanCount = 1
            }
            false
        }
        initView()
        vm.start()
    }

    private fun initView() {
        adapter.setOnItemClickListener { _, _, position ->
            onStartImagePreview(position)
        }
        adapter.setNoDataView(NoDataView(this).setNoDataMsg("暂无历史记录"))
        binding.rv.layoutManager = sm
        binding.rv.adapter = adapter
        adapter.onUpdateList(list)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_il, menu)
        return super.onCreateOptionsMenu(menu)
    }


    //跳转开始预览
    private fun onStartImagePreview(position: Int) {
        val photoList = ArrayList<ImagePreviewBean>()
        list.forEach {
            val b = ImagePreviewBean()
            b.objURL = it.path
            b.imageUrl = it.getMaxPath()
            b.width = it.w
            b.height = it.h
            photoList.add(b)
        }
        ImagePreviewActivity.startPhotoPreview(photoList, position)
    }

}