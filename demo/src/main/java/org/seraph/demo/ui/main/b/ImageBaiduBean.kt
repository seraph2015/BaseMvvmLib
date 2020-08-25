package org.seraph.demo.ui.main.b

import org.seraph.demo.utils.urlToBaiduComplie
import java.io.Serializable


data class ImageBaiduBean(val data: List<BaiduImage>) : Serializable

data class BaiduImage(
    var thumbURL: String?,
    var middleURL: String?,
    var hoverURL: String?,
    var objURL: String?,

    var width: Int = 0,
    var height: Int = 0,

    var fromPageTitle: String?,

    /**
     * 图片类型
     */
    var type: String?,

    var isShowTitle: Boolean = false
) : Serializable {

    /**
     * 当前objURL被加密，需要解密
     */
    fun getBaiduObjURL(): String {
        if (!objURL.isNullOrEmpty()) {
            if (!objURL!!.contains("http")) {
                objURL = objURL.urlToBaiduComplie()
            }
        }
        return objURL ?: ""
    }
}