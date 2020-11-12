package org.seraph.module_welcome.ui.b

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 一言bean
 * date：2018/12/13 16:00
 * author：xiongj
 */
@Parcelize
data class WelcomeYiYanBean(
    //一言的id 可以链接到https://hitokoto.cn?id=[id]查看这个一言的完整信息
    var id: Int = 0,

    //一言正文。编码方式unicode。使用utf-8。
    var hitokoto: String? = null,

    //类型 （a - 动画 b – 漫画 c – 游戏 d – 小说 e – 原创f – 来自网络 g – 其他）
    var type: String? = null,

    //出处
    var from: String? = null,

    //添加者
    var creator: String? = null,

    //添加时间戳
    var created_at: String? = null
) : Parcelable
