package org.seraph.lib.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.seraph.lib.LibConfig

/**
 * ABaseViewModel
 * date：2019/4/25 13:04
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    val mException: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }

    abstract fun start()


    private fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        launchOnUI(block, {
            if (LibConfig.DEBUG) {
                LogUtils.i(it.message)
            }
        })
    }

    fun launchOnUI(
        block: suspend CoroutineScope.() -> Unit,
        exBlock: suspend CoroutineScope.(Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                //如果是取消导致的异常，则不进行通知
                if (e !is CancellationException) {
                    mException.value = e
                    exBlock(e)
                }
            }
        }
    }

}