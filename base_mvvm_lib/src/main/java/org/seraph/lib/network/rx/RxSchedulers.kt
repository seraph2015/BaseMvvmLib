package org.seraph.lib.network.rx

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * rxjava线程转换
 * date：2019/4/19 12:00
 * author：xiongj
 * mail：417753393@qq.com
 **/
object RxSchedulers {

    /**
     * io线程转main线程
     */
    @JvmStatic
    fun <T> io_main(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }


    /**
     * io线程转main线程
     */
    @JvmStatic
    fun <T> io_main_o(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

}