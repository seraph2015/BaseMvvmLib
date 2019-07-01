package org.seraph.lib

/**
 * 部分app通用设置配置
 * date：2019/5/21 15:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
object LibConfig {

        /**
         * 是否调试模式
         */
        const val DEBUG = false

        /**
         * 网络请求默认超时时间（秒）
         */
        const val DEFAULT_TIMEOUT: Long = 30
        /**
         * 默认数据查询数量
         */
        const val PAGE_SIZE: Int = 48
        /**
         * 最大缓存占用3g
         */
        const val CACHE_MAX_SIZE: Long = 3L * 1024 * 1024 * 1024
        /**
         * 存储文件夹名称
         */
        const val APP_NAME = "Seraph"
}