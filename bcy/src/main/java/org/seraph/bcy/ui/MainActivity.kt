package org.seraph.bcy.ui

import android.view.Menu
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.seraph.bcy.AppConstants
import org.seraph.bcy.R
import org.seraph.bcy.databinding.ActivityMainBinding
import org.seraph.bcy.ui.vm.MainVm
import org.seraph.lib.ui.base.ABaseActivity
import java.util.*
import javax.inject.Inject


@Route(path = AppConstants.PATH_APP_MAIN)
@ActivityScoped
@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding>(R.layout.activity_main) {

//    override fun getViewModelClass(): Class<MainVm> {
//        return MainVm::class.java
//    }

    @Inject
    lateinit var vm: MainVm

    override fun init() {
        setSupportActionBar(binding.tb)
        binding.tb.setOnMenuItemClickListener {
            binding.etBcyIamgeJson.setText("")
            false
        }
        initView()
        vm.start()

    }

    /**
     * 初始化view
     */
    private fun initView() {
        binding.btnBcyImage.setOnClickListener {
            vm.onSetJsonStr(
                binding.etBcyIamgeJson.text.toString().trim()
            )
        }
        binding.btnJsonLs.setOnClickListener {
            ARouter.getInstance().build(AppConstants.BCY_SEARCH_HISTORY).navigation()
        }
        binding.btnCache1.setOnClickListener { binding.tvCachePath1.text = cacheDir.absolutePath }
        binding.btnCache2.setOnClickListener {
            binding.tvCachePath2.text = externalCacheDir?.absolutePath
        }

        binding.btnBarH.setOnClickListener {
            val statusBar = SizeUtils.px2dp(BarUtils.getStatusBarHeight().toFloat())
            val actionBar = SizeUtils.px2dp(BarUtils.getActionBarHeight().toFloat())
            val navBar = SizeUtils.px2dp(BarUtils.getNavBarHeight().toFloat())
            binding.tvBarH.text = String.format(
                Locale.getDefault(),
                "statusBar:%d actionBar:%d navBar:%d",
                statusBar,
                actionBar,
                navBar
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_text_input, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
