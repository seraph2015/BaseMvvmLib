package org.seraph.demo.ui.main

import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ScreenUtils
import dagger.hilt.android.AndroidEntryPoint
import org.seraph.demo.LoveConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActivityImagesBinding
import org.seraph.demo.ui.main.vm.ImagesVm
import org.seraph.lib.ui.base.ABaseActivity


@Route(path = LoveConstants.PATH_LOVE_IMAGES)
@AndroidEntryPoint
class ImagesActivity : ABaseActivity<ActivityImagesBinding, ImagesVm>(R.layout.activity_images) {


    override fun bindVM(): ImagesVm {
        return viewModels<ImagesVm>().value
    }

    override fun init() {
        ScreenUtils.setFullScreen(this)
        binding.vm = vm
        binding.ivBack.setOnClickListener { onBackPressed() }
        val gLayoutManager = GridLayoutManager(this, 5, RecyclerView.HORIZONTAL, false)
        binding.rvImages.layoutManager = gLayoutManager
        binding.rvImages.adapter = vm.adapter
        vm.listData.observe(this, Observer {
            vm.adapter.onUpdateList(it)
        })
        binding.wb.setBackgroundColor(ColorUtils.string2Int("#33000000"))
        binding.wb.loadUrl("file:///android_asset/girl2/girl.html")
        vm.start()
    }

    override fun onPause() {
        super.onPause()
        binding.wb.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.wb.onResume()
    }

    public override fun onDestroy() {
        // mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
        // destory()
        val parent = binding.wb.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(binding.wb)
        }

        binding.wb.stopLoading()
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        binding.wb.settings.javaScriptEnabled = false
        binding.wb.clearHistory()
        binding.wb.removeAllViews()
        try {
            binding.wb.destroy()
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_null, R.anim.anim_go_center_show_to_gone)
    }


}
