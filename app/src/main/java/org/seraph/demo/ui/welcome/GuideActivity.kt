package org.seraph.demo.ui.welcome

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ConvertUtils
import com.tmall.ultraviewpager.UltraViewPager
import kotlinx.android.synthetic.main.act_guide.*
import org.seraph.demo.AppConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActGuideBinding
import org.seraph.demo.ui.welcome.vm.GuideVm
import org.seraph.lib.ui.base.ABaseActivity
import org.seraph.lib.ui.base.ABasePagerAdapter

/**
 * 引导页
 * date：2019/4/24 09:23
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Route(path = AppConstants.PATH_WELCOME_GUIDE)
class GuideActivity : ABaseActivity<ActGuideBinding, GuideVm>(R.layout.act_guide) {
    override fun getViewModelClass(): Class<GuideVm> {
        return GuideVm::class.java
    }

    override fun init() {

        initViewPager()

        vm.images.observe(this, Observer { t ->
            run {
                vm.guidePagerAdapter.setList(t)
                binding.ultraViewpager.refresh()
            }
        })

        vm.start()
    }

    /**
     * 初始化ultraViewpager
     */
    private fun initViewPager() {
        vm.guidePagerAdapter.setOnItemClickListener(object : ABasePagerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (position == vm.images.value!!.size - 1) {//最后一页点击跳转
                    ARouter.getInstance().build(AppConstants.PATH_APP_MAIN).navigation()
                    finish()
                }
            }
        })
        binding.ultraViewpager.setOffscreenPageLimit(3)
        binding.ultraViewpager.adapter = vm.guidePagerAdapter
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