package org.seraph.lib.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import javax.inject.Inject

/**
 * ABaseFragment
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseFragment<T : ViewDataBinding>(private val layoutResID: Int) :
    Fragment() {

    /**
     * view
     */
    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 初始化 Binding
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //绑定生命周期
        binding.lifecycleOwner = this
        init()
    }

    abstract fun init()


}