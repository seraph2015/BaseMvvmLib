package org.seraph.demo

/**
 * app配置类
 */
class AppConfig {

    companion object {
        /**
         * 是否调试模式
         */
        const val DEBUG = false

        /**
         * 默认数据查询数量
         */
        const val PAGE_SIZE: Int = 48

        /**
         * 数据库表名称
         */
        const val DB_NAME = "db_seraph"

    }

}
