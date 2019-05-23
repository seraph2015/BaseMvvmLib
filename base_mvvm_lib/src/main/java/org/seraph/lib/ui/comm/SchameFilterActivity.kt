package org.seraph.lib.ui.comm

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter


/**
 * 通过URL跳转
 * date：2019/1/4 11:44
 * author：xiongj
 */
class SchameFilterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent.data
        if (uri != null) {
            ARouter.getInstance().build(uri).navigation()
        }
        finish()
    }

}
