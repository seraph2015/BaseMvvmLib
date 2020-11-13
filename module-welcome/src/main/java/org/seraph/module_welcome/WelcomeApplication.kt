package org.seraph.module_welcome

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * app初始化
 * date：2019/4/18 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
@HiltAndroidApp
class WelcomeApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var welcomeAppLike: WelcomeAppLike

    override fun onCreate() {
        super.onCreate()
        welcomeAppLike.onCreate()
    }


}