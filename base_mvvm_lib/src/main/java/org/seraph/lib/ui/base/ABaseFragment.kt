package org.seraph.lib.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * ABaseFragment
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseFragment<T : ViewDataBinding, vm : ViewModel>(private val layoutResID: Int) :
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
    protected lateinit var vm: vm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 初始化 Binding
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding.root
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 绑定
        vm = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())

        //绑定生命周期
        binding.lifecycleOwner = this
        // DataBinding
        init()
    }


    abstract fun getViewModelClass(): Class<vm>

    abstract fun init()

    /**
     * 自动解绑rxjava（在指定的生命周期）
     */
    fun <T> bindLifecycle(untilEvent: Lifecycle.Event): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, untilEvent))
    }

    /**
     * 自动解绑rxjava（在结束的时候）
     */
    fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        return bindLifecycle(Lifecycle.Event.ON_DESTROY)
    }

}