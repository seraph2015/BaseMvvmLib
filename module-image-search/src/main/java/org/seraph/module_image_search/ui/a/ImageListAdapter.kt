package org.seraph.module_image_search.ui.a

import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseAdapter
import org.seraph.lib.view.CustomWHImageView
import org.seraph.module_image_search.R
import org.seraph.module_image_search.ui.b.CategoryDataBean
import javax.inject.Inject

/**
 * org.seraph.ktmvvm.ui.main
 * date：2019/4/19 18:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ImageListAdapter @Inject constructor() :
    ABaseAdapter<CategoryDataBean, BaseViewHolder>(R.layout.module_image_search_comm_item_image) {


    override fun convert(helper: BaseViewHolder, item: CategoryDataBean) {
        val imageView: CustomWHImageView = helper.getView(R.id.image)
        // imageView.setSize(item.width, item.height)
        //按照控件的大小来缩放图片的尺寸
        GlideApp.with(mContext).load(item.url).into(imageView)
    }


}