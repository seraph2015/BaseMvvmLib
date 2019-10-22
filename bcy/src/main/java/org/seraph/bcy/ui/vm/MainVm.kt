package org.seraph.bcy.ui.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonSyntaxException
import org.seraph.bcy.AppConstants
import org.seraph.bcy.data.repository.DBRepository
import org.seraph.bcy.ui.b.BcyImageBean
import org.seraph.lib.ui.base.ABaseViewModel
import javax.inject.Inject

/**
 * 主页
 * date：2019/5/23 11:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
class MainVm @Inject constructor(
    application: Application,
    private var dbRepository: DBRepository
) :
    ABaseViewModel(application) {

    override fun start() {

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
                .build(AppConstants.BCY_IMAGE_LIST)
                .withSerializable("list", list)
                .navigation()
            dbRepository.saveSearchBcyToDB(jsonStr)
        }


    }


}

