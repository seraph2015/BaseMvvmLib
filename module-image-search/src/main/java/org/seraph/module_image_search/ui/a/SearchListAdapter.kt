package org.seraph.module_image_search.ui.a

import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.module_image_search.R
import org.seraph.lib.ui.base.ABaseAdapter
import javax.inject.Inject

/**
 * org.seraph.all.ui.module.main.adapter
 * date：2019/1/2 18:28
 * author：xiongj
 */
class SearchListAdapter @Inject
constructor() : ABaseAdapter<String, BaseViewHolder>(R.layout.module_image_search_act_main_search_item) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_search_text, item)
    }
}
