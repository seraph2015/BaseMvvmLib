package org.seraph.lib.view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import org.seraph.lib.R

/**
 * 列表适配器加载更多视图view
 * date：2019/4/23 10:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
open class AdapterFootView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.afv_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.lav_loading
    }

    override fun getLoadEndViewId(): Int {
        return R.id.tv_loading_end
    }

    override fun getLoadFailViewId(): Int {
        return R.id.tv_loading_fail
    }
}