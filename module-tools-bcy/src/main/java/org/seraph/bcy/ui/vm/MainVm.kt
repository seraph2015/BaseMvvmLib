package org.seraph.bcy.ui.vm

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import org.seraph.bcy.BcyConstants
import org.seraph.bcy.data.repository.DBRepository
import org.seraph.bcy.ui.b.BcyImageBean
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(
    private var dbRepository: DBRepository
) :
    ABaseViewModel() {

    override fun start(vararg any: Any?) {
    }

    fun onSetJsonStr(jsonStr: String) {
        if (jsonStr.isEmpty()) {
            return
        }
        val bcyImageBean: BcyImageBean?
        try {
            bcyImageBean = GsonUtils.fromJson(jsonStr, BcyImageBean::class.java)
        } catch (e: JsonSyntaxException) {
            ToastUtils.showShort("json解析失败")
            e.printStackTrace()
            return
        }
        bcyImageBean?.let {
            //预览当前json保护的图片列表界面
            val list = it.data.multi
            ARouter.getInstance()
                .build(BcyConstants.BCY_IMAGE_LIST)
                .withSerializable("list", list)
                .navigation()
            dbRepository.saveSearchBcyToDB(jsonStr)
        }


    }




}

