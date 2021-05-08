package org.seraph.lib.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import org.seraph.lib.R

/**
 * ABaseActivity
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
        //初始化ARouter
        ARouter.getInstance().inject(this)
        // 初始化 Binding
        binding = DataBindingUtil.setContentView(this, layoutResID)
        vm = bindVM()
        initVMtoUI()
        //绑定生命周期
        binding.lifecycleOwner = this
        initStatusBarMode()
        init()
    }

    /**
     * 初始化一些vm通用的ui操作
     */
    private fun initVMtoUI() {
        vm.onCloseActivity.observe(this, {
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
    protected fun initStatusBarMode(isLightMode: Boolean = true) {
        BarUtils.setStatusBarLightMode(this, isLightMode)
    }

    /**
     * 初始化toolbar默认操作
     */
    protected fun initToolbar(
        toolbar: Toolbar,
        @DrawableRes navigationResId: Int? = R.mipmap.comm_ic_back2,
        onNavigationClickListener: View.OnClickListener? = View.OnClickListener { onBackPressed() },
        @DrawableRes menuResId: Int? = null,
        onMenuItemListener: Toolbar.OnMenuItemClickListener? = null
    ): Toolbar {
       // setSupportActionBar(toolbar)
        navigationResId?.let {
            toolbar.setNavigationIcon(it)
        }
        toolbar.setNavigationOnClickListener {
            onNavigationClickListener?.onClick(it)
        }
        menuResId?.let {
            toolbar.inflateMenu(it)
        }
        onMenuItemListener?.let {
            toolbar.setOnMenuItemClickListener(it)
        }
        return toolbar
    }


}