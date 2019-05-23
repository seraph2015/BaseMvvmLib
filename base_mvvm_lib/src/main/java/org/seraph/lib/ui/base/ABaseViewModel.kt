package org.seraph.lib.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * ABaseViewModel
 * date：2019/4/25 13:04
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    //    /**
//     * 事件集合
//     */
//    private var listDisposable: List<Disposable> = ArrayList()
//
//
    abstract fun start()
//
//    override fun onCleared() {
//        super.onCleared()
//        for (disposable in listDisposable) {
//            if (!disposable.isDisposed) {
//                disposable.dispose()
//            }
//        }
//    }
//
//    /**
//     * 添加到集合
//     */
//    fun addDisposable(disposable: Disposable?) {
//        if (disposable == null) {
//            return
//        }
//        listDisposable.plus(disposable)
//    }


}