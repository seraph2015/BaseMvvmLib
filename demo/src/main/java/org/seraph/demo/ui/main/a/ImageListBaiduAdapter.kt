package org.seraph.demo.ui.main.a

import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.demo.R
import org.seraph.demo.ui.main.b.ImageBaiduBean
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseAdapter
import org.seraph.lib.view.CustomWHImageView
import javax.inject.Inject

/**
 * org.seraph.ktmvvm.ui.main
 * date：2019/4/19 18:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ImageListBaiduAdapter @Inject constructor() :
    ABaseAdapter<ImageBaiduBean.BaiduImage, BaseViewHolder>(R.layout.comm_item_image) {


    override fun convert(helper: BaseViewHolder, item: ImageBaiduBean.BaiduImage) {
        val imageView: CustomWHImageView = helper.getView(R.id.image)
        imageView.setSize(item.width, item.height)
        //按照控件的大小来缩放图片的尺寸
        GlideApp.with(mContext).load(item.objURL).into(imageView)
    }


}