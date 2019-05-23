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
object LibUtils {


    /**
     * 去掉指定开头和结尾之间的字符串
     */
    @JvmStatic
    fun stringReplace(str: String, beginStr: String, endStr: String): String {
        val begin = str.indexOf(beginStr)
        if (begin == -1) {
            return str
        }
        val end = str.lastIndexOf(endStr)
        if (end == -1) {
            return str
        }
        return if (begin > end) {
            str
        } else str.replace(str.substring(begin, end + 1), "")
    }


    /**
     * 保存图片文件到磁盘
     * @param tempFile 图片文件源
     * @param imageUrl 图片网络地址
     * @param context 上下文
     */
    @JvmStatic
    fun saveFileToDisk(tempFile: File, imageUrl: String, context: Context): String {
        //获取图片真实的格式
        val tempHz = ImageUtils.getImageType(tempFile) ?: return "获取图片格式失败"
        val saveImageName = EncryptUtils.encryptMD5ToString(imageUrl) + "." + tempHz
        val dcimFile = getDCIMFile(saveImageName) ?: return "图片已存在"
        //复制文件
        return if (FileUtils.copyFile(tempFile, dcimFile)) {
            // 最后通知图库更新此图片
            scanAppImageFile(context, saveImageName)
            "保存成功"
        } else {
            "保存失败"
        }
    }


    /**
     * 获取相册路径文件
     */
    private fun getDCIMFile(imageName: String): File? {

        //图片文件夹下特定文件
        val file = File(PathUtils.getExternalDcimPath() + "/" + LibConfig.APP_NAME, imageName)

        return if (FileUtils.isFileExists(file) && file.length() > 0) {
            null
        } else {
            file
        }
    }

    /**
     * 通知扫描相册对应文件
     */
    private fun scanAppImageFile(context: Context, fileName: String) {
        val photoPath = PathUtils.getExternalDcimPath() + "/" + LibConfig.APP_NAME + "/" + fileName
        galleryAddPic(context, photoPath)
        // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + AppConfig.SAVE_IMAGE_FOLDERS_NAME + "/" + fileName)));
    }

    /**
     * 添加照片到画廊
     *
     * @param currentPhotoPath 照片路径
     */
    private fun galleryAddPic(context: Context, currentPhotoPath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(currentPhotoPath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }

}