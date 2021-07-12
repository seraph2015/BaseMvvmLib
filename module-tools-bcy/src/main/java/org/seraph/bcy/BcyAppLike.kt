package org.seraph.bcy

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.seraph.lib_comm.BaseAppLike
import javax.inject.Inject

/**
 * app初始化
 **/
class BcyAppLike @Inject constructor(@ApplicationContext context: Context) :
    BaseAppLike(context as Application) {

    fun onCreate() {
        super.onCreate(BcyConstants.DEBUG)

    }

}