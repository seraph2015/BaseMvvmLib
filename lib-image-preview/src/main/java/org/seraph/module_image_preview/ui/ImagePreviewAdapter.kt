package org.seraph.module_image_preview.ui

import android.content.Context
import android.graphics.PointF
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.ui.base.ABasePagerAdapter
import org.seraph.lib.utlis.getBitmapDegree
import org.seraph.module_image_preview.R
import javax.inject.Inject


/**
 * 图片预览适配器
 */
class ImagePreviewAdapter @Inject constructor(@ActivityContext private val mContext: Context) :
    ABasePagerAdapter<ImagePreviewBean>(R.layout.module_image_preview_act_photo_preview_item) {

    /**
     * 指定刷新位置
     */
    private var updatePage = -1


    override fun convert(t: ImagePreviewBean, itemView: View, position: Int) {
        val rootView: FrameLayout = itemView.findViewById(R.id.fl_root)
        val sView: SubsamplingScaleImageView = itemView.findViewById(R.id.ssiv_image)
        val gifView: ImageView = itemView.findViewById(R.id.gif_image)
        sView.visibility = View.VISIBLE
        gifView.visibility = View.GONE
        rootView.tag = position
        sView.setDoubleTapZoomScale(2f)
        sView.maxScale = 3f
        sView.minScale = 1f
        sView.setOnImageEventListener(object :
            SubsamplingScaleImageView.DefaultOnImageEventListener() {

            override fun onImageLoadError(e: Exception?) {
                //此控件不支持gif。如果图片为gif.则会出现解析错误,使用普通imageView展示
                sView.visibility = View.GONE
                gifView.visibility = View.VISIBLE
                GlideApp.with(itemView.context)
                    .asGif()
                    .load(t.objURL)
                    .onlyRetrieveFromCache(true)
                    .error(R.mipmap.ic_image_error)
                    .into(gifView)
            }
        })
        sView.setOnClickListener { mOnItemClickListener?.onItemClick(position, sView) }
        gifView.setOnClickListener { mOnItemClickListener?.onItemClick(position, gifView) }

        if (ImagePreviewVm.IMAGE_TYPE_LOCAL == t.fromType && !t.objURL!!.contains("http://") && !t.objURL!!.contains("https://")) {
            sView.setImage(ImageSource.uri(t.objURL!!))
            //获取图片的旋转角度
            sView.orientation = t.objURL.getBitmapDegree()
        } else {
            //从缓存中加载原始图片
            onLoadMaxImage(t, sView)
        }
    }

    override fun getItemPosition(any: Any): Int {
        val view = any as View
        val tag: Int? = view.tag as Int
        return if (tag != null && updatePage >= 0 && tag == updatePage) {
            POSITION_NONE //刷新界面
        } else {
            POSITION_UNCHANGED
        }
    }


    /**
     * 指定位置刷新
     */
    fun setUpdatePage(updatePage: Int) {
        this.updatePage = updatePage
        notifyDataSetChanged()
    }


    /**
     * 从缓存中加载原图
     */
    private fun onLoadMaxImage(
        previewBean: ImagePreviewBean,
        scaleImageView: SubsamplingScaleImageView
    ) {
        //如没有原始图片地址或者原始图片和当前图片一样，则加载小图片地址
        if (previewBean.imageUrl.isEmpty() || previewBean.imageUrl == previewBean.objURL) {
            onLoadMinImage(previewBean.objURL, scaleImageView)
        } else {
            (mContext as ImagePreviewActivity).vm.launchOnUI({
                val fileFuture =
                    GlideApp.with(scaleImageView.context).asFile().load(previewBean.imageUrl)
                        .onlyRetrieveFromCache(true)
                        .submit()
                val file = withContext(Dispatchers.IO) {
                    return@withContext fileFuture.get()
                }
                scaleImageView.setImage(
                    ImageSource.uri(file.absolutePath),
                    ImageViewState(0f, PointF(0f, 0f), 0)
                )
            }, {
                //图片没有缓存，显示下载原图，加载当前图片
                onLoadMinImage(previewBean.objURL, scaleImageView)
            })
        }
    }

    /**
     * 加载默认图片
     */
    private fun onLoadMinImage(objUrl: String?, scaleImageView: SubsamplingScaleImageView) {
        scaleImageView.setImage(
            ImageSource.resource(R.mipmap.ic_image_placeholder),
            ImageViewState(0f, PointF(0f, 0f), 0)
        )
        //使用后台线程下载
        (mContext as ImagePreviewActivity).vm.launchOnUI({
            val fileFuture = GlideApp.with(scaleImageView.context).asFile().load(objUrl).submit()
            val file = withContext(Dispatchers.IO) {
                return@withContext fileFuture.get()
            }
            scaleImageView.setImage(
                ImageSource.uri(file.absolutePath),
                ImageViewState(0f, PointF(0f, 0f), 0)
            )
        }, {
            scaleImageView.setImage(
                ImageSource.resource(R.mipmap.ic_image_error),
                ImageViewState(0f, PointF(0f, 0f), 0)
            )
        })
    }


}
