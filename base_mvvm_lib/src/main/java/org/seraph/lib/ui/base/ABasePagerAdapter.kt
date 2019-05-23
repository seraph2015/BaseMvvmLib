package org.seraph.lib.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import java.util.*

/**
 * ABasePagerAdapter
 * date：2017/5/4 15:47
 * author：Seraph
 */
abstract class ABasePagerAdapter<T> constructor(@LayoutRes var resource: Int) : PagerAdapter() {


    /**
     * 数据源
     */
    private var list = ArrayList<T>()

    /**
     * 点击监听
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    protected var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    fun getItem(position: Int): T {
        return list[position]
    }

    fun setList(listData: List<T>) {
        if (list !== listData) {
            list.clear()
            list.addAll(listData)
        }
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<T> {
        return list
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rootView = LayoutInflater.from(container.context).inflate(resource, container, false)
        convert(getItem(position), rootView, position)
        container.addView(rootView)
        return rootView
    }

    abstract fun convert(t: T, itemView: View, position: Int)


    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}
