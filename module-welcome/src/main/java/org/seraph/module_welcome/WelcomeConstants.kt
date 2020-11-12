package org.seraph.module_welcome

/**
 * 业务常量配置
 * date：2019/4/18 17:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
object WelcomeConstants {

    const val DEBUG: Boolean = true

    /////////////////////////////界面路径/////////////////////////////////////////

    /**
     * 引导页
     */
    const val PATH_WELCOME_GUIDE = "/welcome/guide"
    /**
     * 首页
     */
    const val PATH_APP_MAIN = "/main/main"


    ////////////////////////////偏好//////////////////////////////////

    /**
     * sp数据库名称
     */
    const val SP_NAME = "sp_seraph"

    /**
     * 是否第一次进入APP
     */
    const val IS_FIRST = "is_first"

    /**
     * 欢迎页信息
     */
    const val SP_W_INFO: String = "sp_w_info"


}