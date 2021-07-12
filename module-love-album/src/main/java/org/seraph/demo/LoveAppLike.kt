package org.seraph.demo

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.seraph.lib_comm.BaseAppLike
import javax.inject.Inject

/**
 * app初始化
 **/
class LoveAppLike @Inject constructor(@ApplicationContext context: Context) :
    BaseAppLike(context as Application) {

    fun onCreate() {
        super.onCreate(LoveConstants.DEBUG)

    }

}