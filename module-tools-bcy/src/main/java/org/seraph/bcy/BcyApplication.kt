package org.seraph.bcy

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BcyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var welcomeAppLike: BcyAppLike

    override fun onCreate() {
        super.onCreate()
        welcomeAppLike.onCreate()
    }


}
