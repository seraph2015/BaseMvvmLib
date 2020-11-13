package org.seraph.module_main

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import org.seraph.module_image_search.SearchImageAppLike
import org.seraph.module_welcome.WelcomeAppLike
import javax.inject.Inject

/**
 * app初始化
 **/
@HiltAndroidApp
class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var welcomeAppLike: WelcomeAppLike
    @Inject
    lateinit var searchImageAppLike: SearchImageAppLike

    override fun onCreate() {
        super.onCreate()
        welcomeAppLike.onCreate()
        searchImageAppLike.onCreate()
    }


}