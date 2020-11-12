package org.seraph.module_welcome.ui.a

import android.view.View
import android.widget.ImageView
import org.seraph.lib.ui.base.ABasePagerAdapter
import org.seraph.module_welcome.R
import javax.inject.Inject

/**
 * 引导页适配器
 * date：2019/4/24 10:28
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeGuidePagerAdapter @Inject constructor() : ABasePagerAdapter<Int>(R.layout.module_welcome_act_guide_item) {


    override fun convert(t: Int, itemView: View, position: Int) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_guide)
        imageView.setImageResource(t)
        imageView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position,it)
        }
    }

}