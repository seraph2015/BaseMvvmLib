package org.seraph.demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.seraph.demo.data.network.service.ApiBaiduService
import org.seraph.demo.data.network.service.ApiYiYanService
import org.seraph.demo.ui.main.b.ImageBaiduBean
import org.seraph.demo.ui.welcome.b.YiYanBean
import org.seraph.lib.network.rx.RxSchedulers
import org.seraph.lib.ui.base.ABaseSubscriber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 其它数据
 * date：2019/4/28 17:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
class OtherRepository @Inject constructor(
    private val apiYiYanService: ApiYiYanService,
    private val apiBaiduService: ApiBaiduService
) {


    /**
     * 获取一句话
     */
    fun getYiYan(): LiveData<YiYanBean> {
        val liveData = MutableLiveData<YiYanBean>()
        apiYiYanService.getYiYan("?c=")
            .compose(RxSchedulers.io_main())
            .subscribe(object : ABaseSubscriber<YiYanBean>() {

                override fun onSuccess(t: YiYanBean) {
                    //获取到广告图片地址存起来
                    liveData.value = t
                }

                override fun onError(err: String?) {
                }
            })
        return liveData
    }


    /**
     * 度娘搜索图片
     */
    fun doSearch(pageNo: Int, pageSize: Int, keyWordStr: String): LiveData<List<ImageBaiduBean.BaiduImage>> {
        val liveData = MutableLiveData<List<ImageBaiduBean.BaiduImage>>()
        val start = (pageNo - 1) * pageSize //开始查询的数据
        apiBaiduService.doSearch("resultjsonavatarnew", keyWordStr, start, pageSize)
            .map { t: ImageBaiduBean -> t.imgs }
            .compose(RxSchedulers.io_main())
            .subscribe(object : ABaseSubscriber<List<ImageBaiduBean.BaiduImage>>() {
                override fun onSuccess(t: List<ImageBaiduBean.BaiduImage>) {
                    liveData.value = t
                }

                override fun onError(err: String?) {
                    liveData.value = null
                }
            })
        return liveData
    }


}