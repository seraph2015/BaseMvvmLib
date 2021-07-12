package org.seraph.demo.ui.main.a

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.demo.R
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseAdapter
import javax.inject.Inject

class ImagesAdapter @Inject constructor() :
    ABaseAdapter<Int, BaseViewHolder>(R.layout.act_images_item) {

    override fun convert(helper: BaseViewHolder, item: Int) {
        val imageView = helper.getView<ImageView>(R.id.iv_image)
        GlideApp.with(mContext).load(item).into(imageView)
    }
}