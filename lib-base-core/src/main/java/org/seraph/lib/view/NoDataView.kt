package org.seraph.lib.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import org.seraph.lib.R
import org.seraph.lib.databinding.CommNoDataTextBinding

/**
 * 数据填充视图
 * date：2019/4/19 17:32
 * author：xiongj
 * mail：417753393@qq.com
 **/
class NoDataView constructor(
    context: Context,
    /**
     * 等待加载动画
     */
    loadingAssetName: String? = null,
    private var type: Int = LOADING,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

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

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        type = LOADING,
        attrs = attrs
    )

    private var binding: CommNoDataTextBinding? = null

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.comm_no_data_text,
            null,
            false
        )
        loadingAssetName?.let {
            binding?.lavLoading?.setAnimation(it)
        }
        addView(
            binding?.root,
            0,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
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
     * 设置没有数据的的信息展示
     */
    fun setNoDataMsg(msg: CharSequence? = null, resId: Int = -1): NoDataView {
        this.noDataMsg = msg
        this.noDataResId = resId
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
        setLoadingType(LOADING_OK)
    }

    /**
     * 没有数据
     */
    fun setNoDate() {
        setLoadingType(NO_DATE)
    }

    /**
     * 加载失败（网络异常）
     */
    fun setNetErr() {
        setLoadingType(NET_ERR)
    }

    /**
     * 正在加载
     */
    fun setLoading() {
        setLoadingType(LOADING)
    }

    /**
     * 正在加载
     */
    fun setLoadingType(type: Int) {
        this.type = type
        setType()
    }

    /**
     * 设置状态
     */
    private fun setType() {
        binding?.let {
            when (type) {
                LOADING -> {
                    it.ivIcon.visibility = View.GONE
                    it.tvShowStr.visibility = View.GONE
                    it.lavLoading.visibility = View.VISIBLE
                    it.root.setOnClickListener(null)

                    this.visibility = View.VISIBLE
                }
                NET_ERR -> {
                    it.lavLoading.visibility = View.GONE
                    it.ivIcon.visibility = View.VISIBLE
                    it.tvShowStr.visibility = View.VISIBLE
                    it.tvShowStr.text = "抱歉，你的网络走丢了\n点击重试"
                    it.root.setOnClickListener(this)
                    this.visibility = View.VISIBLE
                }
                NO_DATE -> {
                    it.ivIcon.visibility = View.VISIBLE
                    it.tvShowStr.visibility = View.VISIBLE
                    it.lavLoading.visibility = View.GONE
                    if (noDataResId != -1) {
                        it.ivIcon.setImageResource(noDataResId)
                    }
                    it.tvShowStr.text = if (noDataMsg.isNullOrBlank()) "暂无数据" else noDataMsg
                    //是否设置监听
                    it.root.setOnClickListener(if (noDateIsListener) this else null)
                    this.visibility = View.VISIBLE
                }
                LOADING_OK -> {
                    it.root.setOnClickListener(null)
                    this.visibility = View.GONE
                }
            }
        }
    }

}