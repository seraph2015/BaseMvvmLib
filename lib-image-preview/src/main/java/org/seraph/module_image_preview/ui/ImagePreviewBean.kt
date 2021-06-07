package org.seraph.module_image_preview.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 图片预览数据
 * date：2017/2/28 09:06
 * author：xiongj
 * mail：417753393@qq.com
 */
@Parcelize
data class ImagePreviewBean( //图片当前加载的地址
    var objURL: String? = null,

    //原始图片大小的地址（下载使用，如果没有，则使用显示地址）
    var imageUrl: String = "",

    var width: Int = 0,

    var height: Int = 0,

    var fromPageTitle: String? = null,

    /**
     * 图片的来源类型（本地或者网络）默认网络
     */
    var fromType: String = ""
) : Parcelable
