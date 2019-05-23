package org.seraph.lib.ui.comm.photopreview

import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import org.seraph.lib.LibConstants
import org.seraph.lib.R
import org.seraph.lib.databinding.LibCommActPhotoPreviewBinding
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.ui.base.ABasePagerAdapter
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.CURRENT_POSITION
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.IMAGE_TYPE_LOCAL
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.PHOTO_LIST
import java.util.*

/**
 * 图片预览
 * date：2019/4/26 14:29
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Route(path = LibConstants.PATH_COMM_PHOTO_PREVIEW)
class PhotoPreviewActivity :
    ABaseActivity<LibCommActPhotoPreviewBinding, PhotoPreviewVm>(R.layout.lib_comm_act_photo_preview) {

    override fun getViewModelClass(): Class<PhotoPreviewVm> {
        return PhotoPreviewVm::class.java
    }

    @JvmField
    @Autowired
    var photoList: ArrayList<PhotoPreviewBean>? = null

    @JvmField
    @Autowired
    var currentPosition: Int = 0

    override fun init() {
        binding.vm = vm
        BarUtils.setStatusBarLightMode(this, false)
        initView()

        vm.photoPreviewAdapter.setList(photoList!!)

        if (currentPosition == 0) {
            vm.upDateCurrentPosition(currentPosition)
        } else {
            //设置当前翻页
            binding.vpPhotoPreview.currentItem = currentPosition
        }
    }

    private fun initView() {
        binding.ivBack.setOnClickListener { onBackPressed() }

        //显示原图
        binding.tvOriginalPhoto.setOnClickListener { vm.onDownloadOriginalImage() }

        //下载
        binding.tvSave.setOnClickListener { vm.saveImage() }

        binding.vpPhotoPreview.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                vm.upDateCurrentPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.vpPhotoPreview.offscreenPageLimit = 5


        vm.photoPreviewAdapter.setOnItemClickListener(object : ABasePagerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                vm.onSwitchUIShow()
            }
        })

        binding.vpPhotoPreview.adapter = vm.photoPreviewAdapter


    }


    companion object {
        /**
         * 单图片预览(本地图片)
         */
        @JvmStatic
        fun startPhotoPreview(imagePrat: String) {
            val imageList = ArrayList<String>()
            imageList.add(imagePrat)
            startPhotoPreview(imageList, 0)
        }

        /**
         * 单图片预览（网络图片/网络图片）
         */
        @JvmStatic
        fun startPhotoPreview(bean: PhotoPreviewBean) {
            val imageList = ArrayList<PhotoPreviewBean>()
            imageList.add(bean)
            startPhotoPreview(imageList, 0)
        }

        /**
         * 多图片预览
         *
         * @param currentPosition 当前第几个（从0开始)
         */
        @JvmStatic
        fun <T> startPhotoPreview(imageList: ArrayList<T>, currentPosition: Int) {
            val beanList = ArrayList<PhotoPreviewBean>()
            for (t in imageList) {
                if (t is String) {
                    val previewBean = PhotoPreviewBean()
                    previewBean.objURL = t
                    previewBean.fromType = IMAGE_TYPE_LOCAL
                    beanList.add(previewBean)
                } else {
                    beanList.add(t as PhotoPreviewBean)
                }
            }
            ARouter.getInstance().build(LibConstants.PATH_COMM_PHOTO_PREVIEW)
                .withSerializable(PHOTO_LIST, beanList)
                .withInt(CURRENT_POSITION, currentPosition)
                .navigation()
        }
    }

}