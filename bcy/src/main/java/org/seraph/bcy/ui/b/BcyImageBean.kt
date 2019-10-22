package org.seraph.bcy.ui.b

import java.io.Serializable

/**
 * bcy下载jsonBean
 * date：2019/1/16 15:08
 * author：xiongj
 * mail：417753393@qq.com
 */

/**
 * status : 1
 * data : {"download":false,"multi":[{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/05bb5da0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/04547aa0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/082817e0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/00c4eb40165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/06c0e6c0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/0752ed90165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/08babaf0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1536/fill/I2NjY2NjYw==/dx/50/dy/37/font/5b6u6L2v6ZuF6buR","w":3072,"h":2048},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/ff100f50165311e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072}]}
 */

data class BcyImageBean(
    val data: DataBean
) : Serializable

/**
 * multi : [{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/05bb5da0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/04547aa0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/082817e0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/00c4eb40165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/06c0e6c0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/0752ed90165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/08babaf0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1536/fill/I2NjY2NjYw==/dx/50/dy/37/font/5b6u6L2v6ZuF6buR","w":3072,"h":2048},{"path":"https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/ff100f50165311e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR","w":2048,"h":3072}]
 */
data class DataBean(
    val multi: ArrayList<MultiBean>
) : Serializable

/**
 * path : https://img-bcy-qn.pstatp.com/user/196116/item/web/179yg/05bb5da0165411e9bd475937589a1932.jpg?imageMogr2/auto-orient/strip|watermark/2/text/wqnpmLPngo7lnotueW9ueW9rbwrljYrmrKHlhYMt5LqM5qyh5YWD5Yib5L2c6ICF56S-5Yy6/fontsize/1024/fill/I2NjY2NjYw==/dx/33/dy/25/font/5b6u6L2v6ZuF6buR
 * w : 2048
 * h : 3072
 */
data class MultiBean(
    var path: String,
    var w: Int = 0,
    var h: Int = 0
) : Serializable {

    /**
     * 获取原始无水印的图片地址,如果规则解析失败则使用原地址
     */
    fun getMaxPath(): String {
        var maxPath = path
        val p = path.indexOf("~")
        if (p != -1) {
            val tempPath = maxPath.substring(0, p)
            //如果图片地址包含 https://p*-bcy.byteimg.com/img/banciyuan  则替换为 https://img-bcy-qn.pstatp.com
            if (tempPath.contains("bcy.byteimg.com/img/banciyuan")) {
                maxPath =
                    "https://img-bcy-qn.pstatp.com" + tempPath.split("bcy.byteimg.com/img/banciyuan")[1]
            }
        }
        return maxPath

    }
}
