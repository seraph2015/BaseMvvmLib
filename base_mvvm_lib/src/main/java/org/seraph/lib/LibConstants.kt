package org.seraph.lib

import org.seraph.lib.ui.comm.photopreview.PhotoPreviewBean
import java.util.ArrayList

/**
 * 部分业务常量配置
 * date：2019/4/18 17:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
object LibConstants {

    /////////////////////////////界面路径/////////////////////////////////////////

    /**
     *  图片预览页
     */
    const val PATH_COMM_PHOTO_PREVIEW = "/lib/comm/photoPreview"


    /**
     * 临时存储预览图片路径
     */
    var tempImageList: ArrayList<PhotoPreviewBean>? = null
}