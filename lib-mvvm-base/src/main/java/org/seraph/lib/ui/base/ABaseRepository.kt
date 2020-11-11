package org.seraph.lib.ui.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ABaseRepository {

    /**
     * 使用io线程
     */
    suspend fun <T> apiIoCall(function: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            function.invoke()
        }
    }
}
