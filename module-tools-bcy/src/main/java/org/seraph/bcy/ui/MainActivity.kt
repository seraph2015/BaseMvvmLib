package org.seraph.bcy.ui

import android.view.Menu
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import org.seraph.bcy.BcyConstants
import org.seraph.bcy.R
import org.seraph.bcy.databinding.ActivityMainBinding
import org.seraph.bcy.ui.b.VpnpzBean
import org.seraph.bcy.ui.vm.MainVm
import org.seraph.lib.ui.base.ABaseActivity
import java.nio.charset.Charset
import java.util.*
import kotlin.text.Charsets.UTF_8


@Route(path = BcyConstants.PATH_APP_MAIN)
@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding, MainVm>(R.layout.activity_main) {

    override fun bindVM(): MainVm {
       return viewModels<MainVm>().value
    }

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
        binding.btnBcyImage.setOnClickListener { vm.onSetJsonStr(binding.etBcyIamgeJson.text.toString().trim()) }
        binding.btnJsonLs.setOnClickListener {
            ARouter.getInstance().build(BcyConstants.BCY_SEARCH_HISTORY).navigation()
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
        binding.btn111.setOnClickListener {
            main1()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_text_input, menu)
        return super.onCreateOptionsMenu(menu)
    }

     fun  main1() {
         val s = ResourceUtils.readAssets2String("vpnpz.json")
         val list = GsonUtils.fromJson(s, VpnpzBean::class.java).configs
         val sb:StringBuilder = StringBuilder()
         list.forEach {
             sb.append( it.getSsrFormat()).append("\n")
         }
         System.err.println(sb.toString())
    }


}
