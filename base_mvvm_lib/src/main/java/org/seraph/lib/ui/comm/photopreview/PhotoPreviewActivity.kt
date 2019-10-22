package org.seraph.lib.ui.comm.photopreview

import android.view.View
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
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.DOWNLOAD_IMAGE
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.IMAGE_TYPE_LOCAL
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.PHOTO_LIST
import org.seraph.lib.ui.comm.photopreview.PhotoPreviewVm.Companion.SHOW_MAX_IMAGE
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

//    @JvmField
//    @Autowired
//    var photoList: ArrayList<PhotoPreviewBean>? = null

    @JvmField
    @Autowired
    var currentPosition: Int = 0

    @JvmField
    @Autowired
    var showMaxImage: Boolean = true

    @JvmField
    @Autowired
    var downloadImage: Boolean = true

    override fun init() {
        binding.vm = vm
        BarUtils.setStatusBarLightMode(this, false)
        initView()

        vm.photoPreviewAdapter.setList(LibConstants.tempImageList!!)

        if (currentPosition == 0) {
            vm.upDateCurrentPosition()
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
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                vm.upDateCurrentPosition()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.vpPhotoPreview.offscreenPageLimit = 5


        vm.photoPreviewAdapter.setOnItemClickListener(object :
            ABasePagerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                vm.onSwitchUIShow()
            }
        })

        binding.vpPhotoPreview.adapter = vm.photoPreviewAdapter


    }


    companion object {

        /**
         * 单图片预览（本地图片[String]/网络图片[PhotoPreviewBean]）
         */
        @JvmStatic
        fun <T> startPhotoPreview(
            bean: T,
            isDownLoad: Boolean = true,
            isShowMaxImage: Boolean = true
        ) {
            val imageList = ArrayList<T>()
            imageList.add(bean)
            startPhotoPreview(imageList, isDownLoad = isDownLoad, isShowMaxImage = isShowMaxImage)
        }

        /**
         * 多图片预览 当前第[currentPosition] (默认0)开始
         * @param isDownLoad 是可以下载（显示下载按钮）
         * @param isShowMaxImage 是否可以查看原图（显示查看原图按钮）
         */
        @JvmStatic
        fun <T> startPhotoPreview(
            imageList: List<T>,
            currentPosition: Int = 0,
            isDownLoad: Boolean = true,
            isShowMaxImage: Boolean = true
        ) {

            val beanList = ArrayList<PhotoPreviewBean>()
            imageList.forEach {
                when (it) {
                    is String -> {
                        val previewBean = PhotoPreviewBean()
                        previewBean.objURL = it
                        previewBean.fromType = IMAGE_TYPE_LOCAL
                        beanList.add(previewBean)
                    }
                    is PhotoPreviewBean -> beanList.add(it)

                }
            }
            //防止传递数据大于允许的大小导致异常崩溃
            LibConstants.tempImageList = beanList
            ARouter.getInstance().build(LibConstants.PATH_COMM_PHOTO_PREVIEW)
               // .withSerializable(PHOTO_LIST, beanList)
                .withInt(CURRENT_POSITION, currentPosition)
                .withBoolean(SHOW_MAX_IMAGE, isShowMaxImage)
                .withBoolean(DOWNLOAD_IMAGE, isDownLoad)
                .navigation()
        }
    }

}