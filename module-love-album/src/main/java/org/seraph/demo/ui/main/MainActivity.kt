package org.seraph.demo.ui.main

import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import com.just.agentweb.AbsAgentWebSettings
import com.just.agentweb.AgentWeb
import com.just.agentweb.IAgentWebSettings
import dagger.hilt.android.AndroidEntryPoint
import org.seraph.demo.LoveConstants
import org.seraph.demo.R
import org.seraph.demo.databinding.ActivityMainBinding
import org.seraph.demo.ui.main.vm.MainVm
import org.seraph.lib.ui.base.ABaseActivity


@Route(path = LoveConstants.PATH_LOVE_MAIN)
@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding, MainVm>(R.layout.activity_main) {


    override fun bindVM(): MainVm {
        return viewModels<MainVm>().value
    }

    private var mAgentWeb: AgentWeb? = null

    override fun init() {
        ScreenUtils.setFullScreen(this)
        binding.vm = vm
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.ll, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setAgentWebWebSettings(object : AbsAgentWebSettings() {
                override fun bindAgentWebSupport(agentWeb: AgentWeb?) {

                }

                override fun toSetting(webView: WebView?): IAgentWebSettings<WebSettings> {
                    val iAgentWebSettings: IAgentWebSettings<WebSettings> = super.toSetting(webView)
                    iAgentWebSettings.webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
                    iAgentWebSettings.webSettings.mediaPlaybackRequiresUserGesture = false
                    return iAgentWebSettings
                }
            })
            .createAgentWeb()
            .ready()
            .go("file:///android_asset/love/Love.html")
        mAgentWeb!!.jsInterfaceHolder.addJavaObject("android", AndroidInterface(mAgentWeb!!, this))
    }

    /**
     * 注册给js使用的相关功能
     */
    class AndroidInterface(private val agent: AgentWeb, private val act: MainActivity) {

        @JavascriptInterface
        fun onClick(type: String) {
//            act.runOnUiThread {
//                ARouter.getInstance().build(LoveConstants.PATH_LOVE_IMAGES).withString("type",type)
//                    .withTransition(R.anim.anim_go_center_gone_to_show,R.anim.anim_null)
//                    .navigation(act)
//            }
        }
    }


    /**
     * 双击返回退出
     */
    override fun onBackPressed() {
        vm.doublePressBackToast()
    }


    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    public override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}
