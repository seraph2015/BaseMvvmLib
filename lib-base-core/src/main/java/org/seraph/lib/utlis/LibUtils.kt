package org.seraph.lib.utlis

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.PathUtils
import org.seraph.lib.LibConfig
import java.io.File

/**
 * 工具类
 * date：2019/4/22 09:19
 * author：xiongj
 * mail：417753393@qq.com
 **/


/**
 * 保存图片文件到磁盘
 * @param tempFile 图片文件源
 * @param imageUrl 图片网络地址
 */
fun Context.saveFileToDisk(tempFile: File?, imageUrl: String, extName: String? = null): String {
    if (tempFile == null) {
        return "保存失败"
    }
    //获取图片真实的格式
    val tempHz = ImageUtils.getImageType(tempFile) ?: return "获取图片格式失败"
    val saveImageName =  EncryptUtils.encryptMD5ToString(imageUrl) + "." + (if (tempHz == ImageUtils.ImageType.TYPE_UNKNOWN && extName != null) extName else tempHz.value)
    val dcimFile = saveImageName.getDCIMFile() ?: return "图片已存在"
    //复制文件
    return if (FileUtils.copy(tempFile, dcimFile)) {
        // 最后通知图库更新此图片
        this.scanAppImageFile(saveImageName)
        "保存成功"
    } else {
        "保存失败"
    }
}


/**
 * 获取相册路径文件
 */
private fun String.getDCIMFile(): File? {
    //图片文件夹下特定文件
    val file = File(PathUtils.getExternalDcimPath() + "/" + LibConfig.APP_NAME, this)
    return if (FileUtils.isFileExists(file) && file.length() > 0) null else file
}

/**
 * 通知扫描相册对应文件
 */
private fun Context.scanAppImageFile(fileName: String) {
    val photoPath = PathUtils.getExternalDcimPath() + "/" + LibConfig.APP_NAME + "/" + fileName
    this.galleryAddPic(photoPath)
}

/**
 * 添加照片到画廊 照片路径 [currentPhotoPath]
 */
private fun Context.galleryAddPic(currentPhotoPath: String) {
    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val f = File(currentPhotoPath)
    val contentUri = Uri.fromFile(f)
    mediaScanIntent.data = contentUri
    this.sendBroadcast(mediaScanIntent)
}

