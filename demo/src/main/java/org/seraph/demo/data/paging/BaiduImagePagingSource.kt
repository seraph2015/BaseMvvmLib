package org.seraph.demo.data.paging

import androidx.paging.PagingSource
import org.seraph.demo.AppConfig
import org.seraph.demo.data.network.service.ApiBaiduService
import org.seraph.demo.ui.main.b.BaiduImage

/**
 * Key：分页标识类型，如页码，则为Int
 * Value：返回列表元素的类型，如需要分页的是文章数据，则值应该为文章对象。
 */
class BaiduImagePagingSource(
    private val apiBaiduService: ApiBaiduService,
    private val keyWordStr: String
) : PagingSource<Int, BaiduImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaiduImage> {
        return try {

            val pageNumber = params.key ?: 1
            //获取网络数据
            val response = apiBaiduService.doSearchAsync(
                word = keyWordStr,
                start = (pageNumber - 1) * AppConfig.PAGE_SIZE,
                pageSize = AppConfig.PAGE_SIZE
            )
            return LoadResult.Page(
                //需要加载的数据
                data = response.data,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了 (如果当前需要加载的数据，大于返回的数据，则说明数据没有了，否则加载下一页)
                nextKey = if (AppConfig.PAGE_SIZE> response.data.size - 1) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}