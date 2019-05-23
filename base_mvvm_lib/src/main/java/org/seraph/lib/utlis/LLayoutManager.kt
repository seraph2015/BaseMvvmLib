package org.seraph.lib.utlis

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

/**
 * LinearLayoutManager异常处理
 * date：2019/4/19 18:20
 * author：xiongj
 * mail：417753393@qq.com
 **/
class LLayoutManager @Inject constructor(context: Context) : LinearLayoutManager(context) {


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
