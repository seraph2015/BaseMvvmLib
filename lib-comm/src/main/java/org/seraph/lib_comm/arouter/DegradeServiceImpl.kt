package org.seraph.lib_comm.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.blankj.utilcode.util.LogUtils
import org.seraph.lib_comm.LibCommConstants

/**
 * 全局降级策略
 */
@Route(path = LibCommConstants.PATH_APP_DEGRADE_SERVICE_IMPL)
class DegradeServiceImpl : DegradeService {


    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        LogUtils.i("onLost -> " + postcard.toString())
    }
}