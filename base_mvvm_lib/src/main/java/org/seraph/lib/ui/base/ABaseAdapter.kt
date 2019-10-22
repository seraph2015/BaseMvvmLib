package org.seraph.lib.ui.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.lib.LibConfig
import org.seraph.lib.view.AdapterFootView
import org.seraph.lib.view.NoDataView

/**
 * ABaseAdapter
 * date：2019/4/19 18:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseAdapter<T : Any?, K : BaseViewHolder?> constructor(
    layoutResId: Int = 0,
    data: List<T>? = null
) : BaseQuickAdapter<T, K>(layoutResId, data) {

    /**
     * 数据页码
     */
    private var mPageNo: Int = 0


    /**
     * 当前请求页码
     */
    private var requestPageNo: Int = 1


    /**
     * 获取第一页
     */
    fun getOnePage(): Int {
        requestPageNo = 1
        return requestPageNo
    }

    /**
     * 获取下一页码
     */
    fun getNextPage(): Int {
        requestPageNo = mPageNo + 1
        return requestPageNo
    }

    /**
     * 默认的填充视图
     */
    private var noDataView: NoDataView? = null


    init {
        //默认打开加载更多
        this.setEnableLoadMore(true)
        //设置加载更多的自定义布局
        this.setLoadMoreView(object : AdapterFootView() {})
    }


    /**
     * 设置填充视图
     */
    fun setNoDataView(noDataView: NoDataView) {
        this.noDataView = noDataView
        this.emptyView = this.noDataView
    }

    /**
     * 获取填充视图
     */
    fun getNoDataView(): NoDataView? {
        return this.noDataView
    }

    /**
     * 更新数据
     */
    fun onUpdateList(list: List<T>?): Boolean {
        return onUpdateList(list, null)
    }

    /**
     * 返回值判断总数据源是否有数据
     */
    fun onUpdateList(list: List<T>?, pageSize: Int?): Boolean {
        if (list == null) { //加载失败
            //如果是第一页
            if (requestPageNo == 1) {
                noDataView?.setNetErr()
            }
            loadMoreFail()
            return data.size > 0
        }

        //判断是新数据还是加更多数据
        when (requestPageNo) {
            1 -> {
                setNewData(list)
            }
            else -> {
                addData(list)
            }
        }

        //判断加载更多的状态
        when {
            list.size >= (pageSize ?: LibConfig.PAGE_SIZE) -> { //加载完成
                loadMoreComplete()
                if (requestPageNo == 1) {
                    noDataView?.setLoadingOk()
                }
            }
            else -> {
                loadMoreEnd() //加载结束
                if (requestPageNo == 1 && list.isEmpty()) {
                    noDataView?.setNoDate()
                }
            }
        }

        mPageNo = requestPageNo
        //如果不是第一页 或者 有数据 则返回所有数据list有数据
        return (requestPageNo != 1 || list.isNotEmpty())
    }


}