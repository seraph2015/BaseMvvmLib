package org.seraph.lib.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.android.AndroidInjection
import org.seraph.lib.R
import javax.inject.Inject

/**
 * ABaseActivity
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseActivity<T : ViewDataBinding, vm : ViewModel>(private val layoutResID: Int) :
        AppCompatActivity() {

    /**
     * view
     */
    protected lateinit var binding: T
    /**
     * vm
     */
    protected lateinit var vm: vm

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //一处声明，处处依赖注入
        AndroidInjection.inject(this)
        //初始化ARouter
        ARouter.getInstance().inject(this)
        // 初始化 Binding
        binding = DataBindingUtil.setContentView(this, layoutResID)
        // 绑定
        vm = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        //绑定生命周期
        binding.lifecycleOwner = this
        initTitleBar()
        // DataBinding
        init()
    }


    abstract fun getViewModelClass(): Class<vm>

    abstract fun init()

    /**
     * 初始化标题bar
     */
    private fun initTitleBar() {
        BarUtils.setStatusBarLightMode(this, true)
    }

    /**
     * 初始化toolbar默认操作
     */
    protected fun initToolbar(toolbar: Toolbar): Toolbar {
        return initToolbar(toolbar, true)
    }

    /**
     * 初始化toolbar默认操作
     */
    protected fun initToolbar(toolbar: Toolbar, isDef: Boolean): Toolbar {
        setSupportActionBar(toolbar)
        if (isDef) {
            toolbar.setNavigationIcon(R.mipmap.comm_ic_back_gary)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        return toolbar
    }

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