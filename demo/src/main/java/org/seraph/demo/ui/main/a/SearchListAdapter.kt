package org.seraph.demo.ui.main.a

import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.demo.R
import org.seraph.lib.ui.base.ABaseAdapter
import javax.inject.Inject

/**
 * org.seraph.all.ui.module.main.adapter
 * date：2019/1/2 18:28
 * author：xiongj
 */
class SearchListAdapter @Inject
constructor() : ABaseAdapter<String, BaseViewHolder>(R.layout.act_main_search_item) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_search_text, item)
    }
}
