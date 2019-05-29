package org.seraph.lib.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ABaseViewModel
 * date：2019/4/25 13:04
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    abstract fun start()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return launchOnUI(block, {}, {})
    }

    fun launchOnUI(
        block: suspend CoroutineScope.() -> Unit,
        exBlock: suspend CoroutineScope.(Throwable) -> Unit  = {},
        finallyBlock: suspend CoroutineScope.() -> Unit  = {}
    ): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                //如果是取消导致的异常，则不进行通知
                if (e !is CancellationException) {
                    exBlock(e)
                }
            } finally {
                finallyBlock()
            }
        }
    }

}