package org.seraph.module_image_search

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.seraph.lib_comm.BaseAppLike
import javax.inject.Inject

/**
 * app初始化
 **/
class SearchImageAppLike @Inject constructor(@ApplicationContext context: Context) :
    BaseAppLike(context as Application) {

    fun onCreate() {
        super.onCreate(SearchImageConstants.DEBUG)
    }

}