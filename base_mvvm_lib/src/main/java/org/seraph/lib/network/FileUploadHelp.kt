package org.seraph.lib.network

import com.blankj.utilcode.util.StringUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * 文件上传帮助类
 * date：2019/4/19 11:13
 * author：xiongj
 * mail：417753393@qq.com
 **/
object FileUploadHelp {

    /**
     * 多文件表单上传RequestBody(文件统一接收key)
     *
     * @param params  表单
     * @param files   文件list列表
     * @param fileKey 服务器接收文件key
     */
    fun multipartRequestBody(
        params: Map<String, String>?,
        files: List<File>?,
        fileKey: String
    ): RequestBody {
        val requestBodyBuilder = initParamsBuilder(params)
        if (files != null) {
            for (file in files) {

                requestBodyBuilder.addFormDataPart(
                    if (StringUtils.isEmpty(fileKey)) "file" else fileKey,
                    file.name,
                    file.asRequestBody(MultipartBody.FORM)
                )
            }
        }

        return requestBodyBuilder.build()
    }

    /**
     * 多文件表单上传RequestBody
     *
     * @param params 表单
     * @param files  文件map列表
     */
    fun multipartRequestBody(params: Map<String, String>?, files: Map<String, File>?): RequestBody {
        val requestBodyBuilder = initParamsBuilder(params)
        if (files != null) {
            val fileKeys = files.keys
            for (key in fileKeys) {
                val tempFile = files.getValue(key)
                requestBodyBuilder.addFormDataPart(
                    key,
                    tempFile.name,
                    tempFile.asRequestBody(MultipartBody.FORM)
                )
            }
        }
        return requestBodyBuilder.build()
    }


    /**
     * 初始化和生成公共构建部分 表单[params]
     */
    private fun initParamsBuilder(params: Map<String, String>?): MultipartBody.Builder {
        val requestBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (params != null) {
            val paramsKeys = params.keys
            for (key in paramsKeys) {
                requestBodyBuilder.addFormDataPart(key, params.getValue(key))
            }
        }
        return requestBodyBuilder
    }
}