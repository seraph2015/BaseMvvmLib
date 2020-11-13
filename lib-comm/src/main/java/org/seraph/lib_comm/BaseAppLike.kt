package org.seraph.lib_comm

import android.app.Application

abstract class BaseAppLike(val mContext: Application) {
    abstract fun onCreate()
}