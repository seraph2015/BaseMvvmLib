package org.seraph.demo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * app初始化
 **/
@HiltAndroidApp
class LoveApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var loveAppLike: LoveAppLike

    override fun onCreate() {
        super.onCreate()
        //初始化第三方sdk
        loveAppLike.onCreate()
    }


}