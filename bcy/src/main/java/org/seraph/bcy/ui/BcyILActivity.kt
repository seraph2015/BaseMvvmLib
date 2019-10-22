package org.seraph.bcy.ui

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import org.seraph.bcy.AppConstants
import org.seraph.bcy.R
import org.seraph.bcy.databinding.ActBcyIlBinding
import org.seraph.bcy.ui.a.BcyILAdapter
import org.seraph.bcy.ui.b.MultiBean
import org.seraph.bcy.ui.vm.BcyILVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewActivity
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewBean
import org.seraph.lib.utlis.SGLayoutManager
import org.seraph.lib.view.NoDataView
import javax.inject.Inject

/**
 * 解析图片列表
 * date：2019/10/22 11:30
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Route(path = AppConstants.BCY_IMAGE_LIST)
class BcyILActivity : ABaseActivity<ActBcyIlBinding, BcyILVm>(R.layout.act_bcy_il) {

    override fun getViewModelClass(): Class<BcyILVm> {
        return BcyILVm::class.java
    }

    @Autowired
    lateinit var list: ArrayList<MultiBean>

    @Inject
    lateinit var adapter: BcyILAdapter


    override fun init() {
        initToolbar(binding.tb).title = "图片列表（${list.size}张）"
        initView()
        vm.start()
    }

    private fun initView() {
        adapter.setOnItemClickListener { _, _, position ->
            //进行图片预览
            val listPhoto = ArrayList<PhotoPreviewBean>()
            list.forEach {
                val b = PhotoPreviewBean()
                b.objURL = it.path
                b.imageUrl = it.getMaxPath()
                b.width = it.w
                b.height = it.h
                listPhoto.add(b)
            }
            PhotoPreviewActivity.startPhotoPreview(listPhoto, position)
        }
        adapter.setNoDataView(NoDataView(this).setNoDataMsg("暂无历史记录"))
        binding.rv.layoutManager = SGLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        adapter.onUpdateList(list)
    }
}