package org.seraph.lib.ui.comm.photopreview

import android.graphics.Bitmap
import android.graphics.PointF
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.request.FutureTarget
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import io.reactivex.Flowable
import org.seraph.lib.R
import org.seraph.lib.network.glide.GlideApp
import org.seraph.lib.network.rx.RxSchedulers
import org.seraph.lib.ui.base.ABasePagerAdapter
import org.seraph.lib.ui.base.ABaseSubscriber
import java.io.File
import javax.inject.Inject


/**
 * 图片预览适配器
 */
class PhotoPreviewAdapter @Inject constructor(val activity: PhotoPreviewActivity) :
    ABasePagerAdapter<PhotoPreviewBean>(R.layout.lib_comm_act_photo_preview_item) {

    /**
     * 指定刷新位置
     */
    private var updatePage = -1


    override fun convert(t: PhotoPreviewBean, itemView: View, position: Int) {
        val iv: SubsamplingScaleImageView = itemView.findViewById(R.id.ssiv_image)
        iv.tag = position
        iv.setDoubleTapZoomScale(2f)
        iv.maxScale = 3f
        iv.minScale = 1f
        iv.setOnImageEventListener(object : SubsamplingScaleImageView.DefaultOnImageEventListener() {

            override fun onImageLoadError(e: Exception?) {
                //此控件不支持gif。如果图片为gif.则会出现解析错误，把gif则转成bitmap
                val bitmapFutureTarget =
                    GlideApp.with(activity).asBitmap().onlyRetrieveFromCache(true).load(t.objURL).submit()
                Flowable.just<FutureTarget<Bitmap>>(bitmapFutureTarget)
                    .map<Bitmap> { it.get() }
                    .compose(RxSchedulers.io_main())
                    .`as`(activity.bindLifecycle())
                    .subscribe(object : ABaseSubscriber<Bitmap>() {
                        override fun onSuccess(t: Bitmap) {
                            GlideApp.with(activity).clear(bitmapFutureTarget)
                            iv.setImage(
                                ImageSource.cachedBitmap(t),
                                ImageViewState(0f, PointF(0f, 0f), 0)
                            )
                        }

                        override fun onError(err: String?) {
                            GlideApp.with(activity).clear(bitmapFutureTarget)
                            iv.setImage(
                                ImageSource.resource(R.mipmap.ic_image_error),
                                ImageViewState(0f, PointF(0f, 0f), 0)
                            )
                        }
                    })
            }
        })
        iv.setOnClickListener { mOnItemClickListener?.onItemClick(position) }

        if (PhotoPreviewVm.IMAGE_TYPE_LOCAL == t.fromType && !t.objURL!!.contains("http://")) {
            iv.setImage(ImageSource.uri(t.objURL!!))
        } else {
            //从缓存中加载原始图片
            onLoadMaxImage(t, iv)
        }
    }

    override fun getItemPosition(any: Any): Int {
        val view = any as View
        val tag: Int? = view.tag as Int
        return if (tag != null && updatePage >= 0 && tag == updatePage) {
            PagerAdapter.POSITION_NONE //刷新界面
        } else {
            PagerAdapter.POSITION_UNCHANGED
        }
    }


    //指定位置刷新
    fun setUpdatePage(updatePage: Int) {
        this.updatePage = updatePage
        notifyDataSetChanged()
    }


    //从缓存中加载原图
    private fun onLoadMaxImage(previewBean: PhotoPreviewBean, scaleImageView: SubsamplingScaleImageView) {
        //如没有原始图片地址或者原始图片和当前图片一样，则加载小图片地址
        if (previewBean.imageUrl.isEmpty() || previewBean.imageUrl == previewBean.objURL) {
            onLoadMinImage(previewBean.objURL, scaleImageView)
        } else {
            val fileFuture =
                GlideApp.with(activity).asFile().load(previewBean.imageUrl).onlyRetrieveFromCache(true)
                    .submit()
            Flowable.just<FutureTarget<File>>(fileFuture)
                .map<File> { it.get() }
                .compose(RxSchedulers.io_main())
                .`as`(activity.bindLifecycle())
                .subscribe(object : ABaseSubscriber<File>() {
                    override fun onSuccess(t: File) {
                        GlideApp.with(activity).clear(fileFuture)
                        scaleImageView.setImage(
                            ImageSource.uri(t.absolutePath),
                            ImageViewState(0f, PointF(0f, 0f), 0)
                        )
                    }

                    override fun onError(err: String?) {
                        GlideApp.with(activity).clear(fileFuture)
                        //图片没有缓存，显示下载原图，加载当前图片
                        onLoadMinImage(previewBean.objURL, scaleImageView)
                    }
                })

        }
    }

    //加载默认图片
    private fun onLoadMinImage(objUrl: String?, scaleImageView: SubsamplingScaleImageView) {
        //使用后台线程下载
        val fileFuture = GlideApp.with(activity).asFile().load(objUrl).submit()
        Flowable.just<FutureTarget<File>>(fileFuture)
            .map<File> { it.get() }
            .compose(RxSchedulers.io_main())
            .doOnSubscribe {
                scaleImageView.setImage(
                    ImageSource.resource(R.mipmap.ic_image_placeholder),
                    ImageViewState(0f, PointF(0f, 0f), 0)
                )
            }
            .`as`(activity.bindLifecycle())
            .subscribe(object : ABaseSubscriber<File>() {
                override fun onSuccess(t: File) {
                    GlideApp.with(activity).clear(fileFuture)
                    scaleImageView.setImage(ImageSource.uri(t.absolutePath), ImageViewState(0f, PointF(0f, 0f), 0))
                }

                override fun onError(err: String?) {
                    GlideApp.with(activity).clear(fileFuture)
                    scaleImageView.setImage(
                        ImageSource.resource(R.mipmap.ic_image_error),
                        ImageViewState(0f, PointF(0f, 0f), 0)
                    )
                }
            })

    }


}
