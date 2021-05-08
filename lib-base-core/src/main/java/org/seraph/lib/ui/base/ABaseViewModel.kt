package org.seraph.lib.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.seraph.lib.utlis.onCodeToMessage

/**
 * ABaseViewModel
 **/
abstract class ABaseViewModel : ViewModel() {

    abstract fun start(vararg any: Any?)

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return launchOnUI(block, {}, {})
    }

    fun launchOnUI(
        block: suspend CoroutineScope.() -> Unit,
        exBlock: suspend CoroutineScope.(String) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                //如果是取消导致的异常，则不进行通知
                if (e !is CancellationException) {
                    exBlock(e.onCodeToMessage())
                }
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 关闭当前界面 0 onBackPressed  1 finish
     */
    val onCloseActivity: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}