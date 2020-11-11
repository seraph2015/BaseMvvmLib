package org.seraph.module_image_preview

import org.seraph.module_image_preview.ui.ImagePreviewBean
import java.util.*

/**
 * 部分业务常量配置
 **/
object PreviewConstants {

    /////////////////////////////界面路径/////////////////////////////////////////

    /**
     *  图片预览页
     */
    const val MODULE_IMAGE_PREVIEW_PATH_PREVIEW = "/module/imagePreview/preview"


    /**
     * 临时存储预览图片路径
     */
    var tempImageList: ArrayList<ImagePreviewBean>? = null
}