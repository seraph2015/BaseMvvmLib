package org.seraph.lib.ui.base

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import org.seraph.lib.R

/**
 * ABaseActivity
 * date：2019/4/18 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseActivity<T : ViewDataBinding, VM : ABaseViewModel>(private val layoutResID: Int) :
    AppCompatActivity() {

    /**
     * view
     */
    lateinit var binding: T

    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //一处声明，处处依赖注入
        //初始化ARouter
        ARouter.getInstance().inject(this)
        // 初始化 Binding
        binding = DataBindingUtil.setContentView(this, layoutResID)
        vm = bindVM()
        initVMtoUI()
        //绑定生命周期
        binding.lifecycleOwner = this
        initTitleBar()
        init()
    }

    /**
     * 初始化一些vm通用的ui操作
     */
    private fun initVMtoUI() {
        vm.onCloseActivity.observe(this, Observer {
            when (it) {
                0 -> onBackPressed()
                1 -> finish()
            }
        })
    }

    abstract fun init()

    abstract fun bindVM(): VM

    /**
     * 初始化标题bar
     */
    private fun initTitleBar() {
        BarUtils.setStatusBarLightMode(this, true)
    }

    /**
     * 初始化toolbar默认操作
     */
    protected fun initToolbar(
        toolbar: Toolbar,
        @DrawableRes resId: Int? = R.mipmap.comm_ic_back2
    ): Toolbar {
        setSupportActionBar(toolbar)
        resId?.let {
            toolbar.setNavigationIcon(it)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        return toolbar
    }


}