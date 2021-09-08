package org.seraph.module_image_search

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * app初始化
 **/
//@HiltAndroidApp
class SearchImageApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var searchImageAppLike: SearchImageAppLike

    override fun onCreate() {
        super.onCreate()
        //初始化第三方sdk
        searchImageAppLike.onCreate()
    }


}