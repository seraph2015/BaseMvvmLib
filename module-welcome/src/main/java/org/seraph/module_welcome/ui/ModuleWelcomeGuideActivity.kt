package org.seraph.module_welcome.ui

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ConvertUtils
import com.tmall.ultraviewpager.UltraViewPager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.ui.base.ABasePagerAdapter
import org.seraph.module_welcome.R
import org.seraph.module_welcome.WelcomeConstants
import org.seraph.module_welcome.databinding.ModuleWelcomeActGuideBinding
import org.seraph.module_welcome.ui.a.WelcomeGuidePagerAdapter
import org.seraph.module_welcome.ui.vm.ModuleWelcomeGuideVm
import javax.inject.Inject

/**
 * 引导页
 * date：2019/4/24 09:23
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Route(path = WelcomeConstants.PATH_WELCOME_GUIDE)
@ActivityScoped
@AndroidEntryPoint
class ModuleWelcomeGuideActivity : ABaseActivity<ModuleWelcomeActGuideBinding, ModuleWelcomeGuideVm>(R.layout.module_welcome_act_guide) {

    override fun bindVM(): ModuleWelcomeGuideVm {
        return viewModels<ModuleWelcomeGuideVm>().value
    }

    @Inject
    lateinit var guidePagerAdapter: WelcomeGuidePagerAdapter


    override fun init() {
        initViewPager()
        vm.images.observe(this, { t ->
            guidePagerAdapter.setList(t)
            binding.ultraViewpager.refresh()
        })
        vm.start()
    }

    /**
     * 初始化ultraViewpager
     */
    private fun initViewPager() {
        guidePagerAdapter.setOnItemClickListener(object : ABasePagerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                if (position == vm.images.value!!.size - 1) {//最后一页点击跳转
                    ARouter.getInstance().build(WelcomeConstants.PATH_APP_MAIN).navigation()
                    finish()
                }
            }
        })
        binding.ultraViewpager.setOffscreenPageLimit(3)
        binding.ultraViewpager.adapter = guidePagerAdapter
        //内置indicator初始化
        binding.ultraViewpager.initIndicator()
        //设置indicator样式
        binding.ultraViewpager.indicator
            .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
            .setFocusColor(Color.WHITE)
            .setNormalColor(Color.GRAY)
            .setRadius(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    4f,
                    resources.displayMetrics
                ).toInt()
            )
        //设置indicator对齐方式
        binding.ultraViewpager.indicator.setMargin(10, 10, 10, ConvertUtils.dp2px(15f))
        binding.ultraViewpager.indicator.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        //构造indicator,绑定到UltraViewPager
        binding.ultraViewpager.indicator.build()
    }


}