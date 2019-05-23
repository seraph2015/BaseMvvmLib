package org.seraph.lib.ui.base

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.seraph.lib.network.exception.ServerErrorCode

/**
 * rxjava网络数据接收
 * date：2019/4/19 13:39
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseSubscriber<T> : Subscriber<T> {

    override fun onSubscribe(s: Subscription?) {
        s!!.request(1)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(t: Throwable?) {
        onError(ServerErrorCode.errorCodeToMessageShow(t))
    }

    abstract fun onSuccess(t: T)

    abstract fun onError(err: String?)

    override fun onComplete() {
    }
}