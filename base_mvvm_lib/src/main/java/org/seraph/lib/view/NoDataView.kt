package org.seraph.lib.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.comm_no_data_text.view.*
import org.seraph.lib.R

/**
 * 数据填充视图
 * date：2019/4/19 17:32
 * author：xiongj
 * mail：417753393@qq.com
 **/
class NoDataView constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    private var type: Int = 1
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    companion object {
        /**
         * 正在加载
         */
        const val LOADING = 1

        /**
         * 加载失败
         */
        const val NET_ERR = 2

        /**
         * 没有数据
         */
        const val NO_DATE = 3
        /**
         * 加载完成
         */
        const val LOADING_OK = 4
    }

    /**
     * 提示图片
     */
    private var noDataResId = -1

    /**
     * 提示文字
     */
    private var noDataMsg: CharSequence? = null

    /**
     * 没有数据是否进行重试监听
     */
    private var noDateIsListener = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 1)

    constructor(context: Context, type: Int) : this(context, null, type)

    constructor(context: Context, attrs: AttributeSet?, type: Int) : this(context, attrs, 0, type)

    private var v: View? = null

    init {
        v = LayoutInflater.from(context).inflate(
            R.layout.comm_no_data_text,
            null,
            false
        )
        addView(
            v,
            0,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        )
        setType()
    }

    /**
     * 点击接口
     */
    interface OnNoDataClickListener {
        fun onClick()
    }

    private var onClickListener: OnNoDataClickListener? = null

    /**
     * 重试点击
     */
    override fun onClick(v: View) {
        //如果有监听，则在点击重试，设置为加载状态
        if (onClickListener != null) {
            setLoading()
        }
        onClickListener?.onClick()
    }

    /**
     * 设置点击监听
     */
    fun setOnClickListener(onClickListener: OnNoDataClickListener?): NoDataView {
        this.onClickListener = onClickListener
        return this
    }

    /**
     * 设置没有数据时候提示
     */
    fun setNoDataMsg(noDataResId: Int): NoDataView {
        return setNoDataMsg(noDataResId, null)
    }

    /**
     * 设置没有数据时候提示
     */
    fun setNoDataMsg(noDataMsg: CharSequence): NoDataView {
        return setNoDataMsg(-1, noDataMsg)
    }

    /**
     * 设置没有数据的的信息展示
     */
    fun setNoDataMsg(noDataResId: Int, noDataMsg: CharSequence?): NoDataView {
        this.noDataResId = noDataResId
        this.noDataMsg = noDataMsg
        setType()
        return this
    }

    /**
     * 设置没有数据的时候是否监听
     */
    fun setNoDateIsListener(b: Boolean): NoDataView {
        this.noDateIsListener = b
        setType()
        return this
    }


    /**
     * 加载成功
     */
    fun setLoadingOk() {
        type = LOADING_OK
        setType()
    }

    /**
     * 没有数据
     */
    fun setNoDate() {
        type = NO_DATE
        setType()
    }

    /**
     * 加载失败（网络异常）
     */
    fun setNetErr() {
        type = NET_ERR
        setType()
    }

    /**
     * 正在加载
     */
    fun setLoading() {
        type = LOADING
        setType()
    }

    /**
     * 设置状态
     */
    private fun setType() {
        when (type) {
            LOADING -> {
                iv_icon.visibility = View.GONE
                tv_show_str.visibility = View.GONE
                lav_loading.visibility = View.VISIBLE
                v?.setOnClickListener(null)
                this.visibility = View.VISIBLE
            }
            NET_ERR -> {
                lav_loading.visibility = View.GONE
                iv_icon.visibility = View.VISIBLE
                tv_show_str.visibility = View.VISIBLE
                tv_show_str.text = "抱歉，你的网络走丢了\n点击重试"
                v?.setOnClickListener(this)
                this.visibility = View.VISIBLE
            }
            NO_DATE -> {
                iv_icon.visibility = View.VISIBLE
                tv_show_str.visibility = View.VISIBLE
                lav_loading.visibility = View.GONE
                if (noDataResId != -1) {
                    iv_icon.setImageResource(noDataResId)
                }
                tv_show_str.text = if (noDataMsg.isNullOrBlank()) "暂无数据" else noDataMsg
                //是否设置监听
                v?.setOnClickListener(if (noDateIsListener) this else null)
                this.visibility = View.VISIBLE
            }
            LOADING_OK -> {
                v?.setOnClickListener(null)
                this.visibility = View.GONE
            }
        }
    }

}