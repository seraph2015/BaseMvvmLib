package org.seraph.demo.ui.main.b

import java.io.Serializable


class ImageBaiduBean : Serializable {

    lateinit var imgs: List<BaiduImage>

    class BaiduImage : Serializable {

        var thumbURL: String? = null
        var middleURL: String? = null
        var hoverURL: String? = null
        var objURL: String? = null

        var width: Int = 0
        var height: Int = 0

        var fromPageTitle: String? = null

        /**
         * 图片类型
         */
        var type: String? = null

        var isShowTitle = false
    }
}
