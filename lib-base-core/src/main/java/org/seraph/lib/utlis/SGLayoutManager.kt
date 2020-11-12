package org.seraph.lib.utlis

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import javax.inject.Inject

/**
 *  StaggeredGridLayoutManager异常处理
 * date：2019/4/19 18:20
 * author：xiongj
 * mail：417753393@qq.com
 **/
class SGLayoutManager constructor(spanCount: Int, orientation: Int) :
    StaggeredGridLayoutManager(spanCount, orientation) {

    @Inject
    constructor() : this(2, VERTICAL)

    /**
     * 重写该方法，去捕捉该异常
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {

        }
    }


}
