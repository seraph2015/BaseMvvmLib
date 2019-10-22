package org.seraph.lib.ui.base

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import dagger.android.AndroidInjection
import org.seraph.lib.R
import org.seraph.lib.di.vm.ViewModelFactory
import javax.inject.Inject

/**
 * ABaseActivity
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseActivity<T : ViewDataBinding, VM : ViewModel>(private val layoutResID: Int) :
    AppCompatActivity() {

    /**
     * view
     */
    lateinit var binding: T
    /**
     * vm
     */
    lateinit var vm: VM


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //一处声明，处处依赖注入
        AndroidInjection.inject(this)
        //初始化ARouter
        ARouter.getInstance().inject(this)
        // 初始化 Binding
        binding = DataBindingUtil.setContentView(this, layoutResID)
        // 绑定
        vm = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
        //绑定生命周期
        binding.lifecycleOwner = this
        initTitleBar()
        // DataBinding
        init()
    }


    abstract fun getViewModelClass(): Class<VM>

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
    protected fun initToolbar(toolbar: Toolbar, @DrawableRes resId: Int? = R.mipmap.comm_ic_back2): Toolbar {
        setSupportActionBar(toolbar)
        resId?.let {
            toolbar.setNavigationIcon(it)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        return toolbar
    }


}