package org.seraph.lib.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import org.seraph.lib.di.vm.ViewModelFactory
import javax.inject.Inject

/**
 * ABaseFragment
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseFragment<T : ViewDataBinding, VM : ViewModel>(private val layoutResID: Int) :
    Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    /**
     * view
     */
    protected lateinit var binding: T

    /**
     * vm
     */
    protected lateinit var vm: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 初始化 Binding
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding.root
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 绑定
        vm = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())

        //绑定生命周期
        binding.lifecycleOwner = this
        // DataBinding
        init()
    }


    abstract fun getViewModelClass(): Class<VM>

    abstract fun init()


}