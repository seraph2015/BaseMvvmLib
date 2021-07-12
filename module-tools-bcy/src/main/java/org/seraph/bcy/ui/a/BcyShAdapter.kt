package org.seraph.bcy.ui.a

import android.widget.ImageView

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.bcy.R
import org.seraph.bcy.ui.b.BcyImageBean
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABaseAdapter
import org.seraph.lib_comm.db.table.SearchHistory
import javax.inject.Inject

/**
 * 解析历史适配器
 */
class BcyShAdapter @Inject constructor() :
    ABaseAdapter<SearchHistory, BaseViewHolder>(
        R.layout.act_bcy_sh_item
    ) {
    override fun convert(helper: BaseViewHolder, item: SearchHistory) {

        val ivCover = helper.getView<ImageView>(R.id.iv_cover)
        val bcyList = GsonUtils.fromJson(item.searchKey, BcyImageBean::class.java).data.multi
        val tempPath = bcyList[0].path
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.searchTime))
            .setText(R.id.tv_sl, "${bcyList.size}张")
        GlideApp.with(mContext).load(tempPath).into(ivCover)
    }
}
