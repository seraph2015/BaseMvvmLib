package org.seraph.demo.ui.main.a

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import org.seraph.demo.R
import org.seraph.demo.databinding.CommItemImageBinding
import org.seraph.demo.ui.main.b.BaiduImage
import org.seraph.lib.databinding.CommNoDataTextBinding
import org.seraph.lib.network.glide.GlideApp
import javax.inject.Inject

/**
 * paging3 adapter
 */
class ImageBdAdapter @Inject constructor() :
    PagingDataAdapter<BaiduImage, BindingViewHolder<CommItemImageBinding>>(BeanComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<CommItemImageBinding> {
        val binding = DataBindingUtil.inflate<CommItemImageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.comm_item_image, parent, false
        )
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<CommItemImageBinding>, position: Int) {
        getItem(position)?.let {
            holder.binding.image.setSize(it.width, it.height)
            //按照控件的大小来缩放图片的尺寸
            GlideApp.with(holder.itemView).load(it.getBaiduObjURL()).into(holder.binding.image)
        }
    }
}

/**
 * 布局绑定类
 */
class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

class BeanComparator<B : Any> : DiffUtil.ItemCallback<B>() {
    override fun areItemsTheSame(oldItem: B, newItem: B): Boolean {
        // Id is unique.指定唯一id
        return false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: B, newItem: B): Boolean {
        return oldItem == newItem
    }
}

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.comm_no_data_text, parent, false)
) {
    private val binding = DataBindingUtil.bind<CommNoDataTextBinding>(itemView)!!
    private val progressBar: LottieAnimationView = binding.lavLoading
    private val errorMsg: TextView = binding.tvShowStr
    private val retry: AppCompatImageView = binding.ivIcon
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}


/**
 * 加载状态
 */
class BaseLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent, retry)


}
