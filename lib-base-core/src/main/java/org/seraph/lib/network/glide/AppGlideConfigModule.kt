package org.seraph.lib.network.glide

import android.content.Context
import com.blankj.utilcode.util.SDCardUtils
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.DEFAULT_DISK_CACHE_DIR
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import org.seraph.lib.LibConfig
import java.io.File

/**
 * AppGlide配置
 * date：2019/4/19 14:59
 * author：xiongj
 * mail：417753393@qq.com
 **/
@GlideModule
class AppGlideConfigModule : AppGlideModule() {

    //配置
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        // 判断是否有sd卡，如果有，则使用sd卡进行缓存。否则使用系统空间进行缓存
        builder.setDiskCache(DiskLruCacheFactory(object : CacheDirectoryGetter {
            override fun getCacheDirectory(): File? {
                val cacheDirectory: File? = if (SDCardUtils.isSDCardEnableByEnvironment()) {
                    context.externalCacheDir
                } else {
                    context.cacheDir
                }
                if (cacheDirectory != null) {
                    return File(cacheDirectory, DEFAULT_DISK_CACHE_DIR)
                }
                return cacheDirectory
            }
        }, LibConfig.CACHE_MAX_SIZE))
        //默认请求
        builder.setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .disallowHardwareConfig()
        )
        //日志级别
        //builder.setLogLevel(Log.DEBUG);
    }


    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
