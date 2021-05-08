package org.seraph.lib.ui.base

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * FragmentPager适配器
 **/
abstract class ABaseAdapterFragmentPager<T> constructor(fm: FragmentManager, var list: List<T>) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {}
}