package org.seraph.bcy.ui.a

import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.bcy.R
import org.seraph.bcy.ui.b.MultiBean
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseAdapter
import org.seraph.lib.view.CustomWHImageView
import javax.inject.Inject


class BcyILAdapter @Inject constructor() :
    ABaseAdapter<MultiBean, BaseViewHolder>(R.layout.comm_item_image) {


    override fun convert(helper: BaseViewHolder, item: MultiBean) {
        val imageView: CustomWHImageView = helper.getView(R.id.image)
        imageView.setSize(item.w, item.h)
        //按照控件的大小来缩放图片的尺寸
        GlideApp.with(mContext).load(item.path).into(imageView)
    }


}