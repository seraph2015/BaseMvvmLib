package org.seraph.demo

import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun main() = runBlocking<Unit> {
        println("launch1 ${Thread.currentThread().name}")
        var i = 5
        while (isActive && i >= 0) {
            println("async ${Thread.currentThread().name}  $i ")
            delay(1000L)
            i -= 1
        }
        println("launch2 ${Thread.currentThread().name} ")
    }

    @Test
    fun main2() {
        println("main2 ${Thread.currentThread().name}")
        val job = GlobalScope.launch {
            println("launch ${Thread.currentThread().name} ")
                var i = 5
                while (isActive && i >= 0) {
                    println("withContext ${Thread.currentThread().name}  $i ")
                    delay(1000L)
                    i -= 1
                }
        }

        println("main3 ${Thread.currentThread().name} ")
        runBlocking{
            delay(2000L)
            job.cancelAndJoin()
            println("runBlocking ${Thread.currentThread().name} ")
        }
    }
}
