package org.seraph.demo

import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.seraph.lib.utlis.getStartList

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
        runBlocking {
            delay(2000L)
            job.cancelAndJoin()
            println("runBlocking ${Thread.currentThread().name} ")
        }
    }


    @Test
    fun main3() {
        val str = "abcdefgabcdefg"
        str.getStartList("a").forEach {
            print(it)
        }
    }

    @Test
    fun main4() {
        val myTurtle = Turtle()
        with(myTurtle) {
            // 画一个 100 像素的正方形
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }

    }

    class Turtle {
        fun penDown() {
            println("penDown")
        }

        fun penUp() {
            println("penUp")
        }

        fun turn(degrees: Double) {
            println("turn $degrees")
        }

        fun forward(pixels: Double) {
            println("forward $pixels")
        }
    }

    @Test
    fun main5() {
        val items = listOf(1, 2, 3, 4, 5)

// Lambdas 表达式是花括号括起来的代码块。
        val a = items.fold(0) {
            // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
                acc: Int, i: Int ->
            print("acc = $acc, i = $i, ")
            val result = acc + i
            println("result = $result")
            // lambda 表达式中的最后一个表达式是返回值：
            result
        }

        println("a=$a")
    }

    inline fun <reified T : Any> Gson.fromJson1(json: JsonElement): T = this.fromJson(json, T::class.java)


    class IntTransformer : (Int) -> Int {

        override operator fun invoke(x: Int): Int = aaa(1)


        val aaa = { x: Int ->
            println("x=$x")
            x + 1
        }

        fun aaa(x: Int): Int {
            println("x=$x")
            return x + 1
        }

    }

    val a = { i: Int -> i + 1 }

    val intFunction: (Int) -> Int = IntTransformer()


    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }

    val twoParameters: (String, Int) -> String = repeatFun // OK

    fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }

    val stringPlus: (String, String) -> String = String::plus

    val intPlus: Int.(Int) -> Int = Int::plus

    val intPlus1: Int.(String) -> Int = { this + 1 }


    @Test
    fun main6() {
        // val result = runTransformation(repeatFun) // OK

//        println(stringPlus.invoke("<-", "->"))
//        println(stringPlus("Hello, ", "world!"))
//
//        println(intPlus.invoke(1, 1))
//        println(intPlus(1, 2))
//        println(2.intPlus(3)) // 类扩展调用
        println(2.intPlus1("3")) // 类扩展调用


        html {
            // 带接收者的 lambda 由此开始
            body()   // 调用该接收者对象的一个方法
        }
    }

    class HTML {
        fun body() {}
        fun body2() {}
    }

    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()  // 创建接收者对象
        html.init()        // 将该接收者对象传给该 lambda
        return html
    }

    fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
        this.filterTo(shortWords) { it.length <= maxLength }
        // throwing away the articles
        val articles = setOf("a", "A", "an", "An", "the", "The")
        shortWords -= articles
    }

    @Test
    fun main7() {
//        val words = "A long time ago in a galaxy far far away".split(" ")
//        val shortWords = mutableListOf<String>()
//        words.getShortWordsTo(shortWords, 3)
//        println(shortWords)

//        val doubled = List(101) { it }  // or MutableList if you want to change its content later
//
//        var aaa = 0
//
//        doubled.forEach {
//            aaa += it
//        }
//
//        println("aaa=$aaa")

//        val h = doubled.fold(0) { acc, i ->
//            val a = acc + i
//            print("|a=$a|")
//            a
//        }
//        println()
//        println(h)


//        val sourceList = mutableListOf(1, 2, 3)
//        val copyList = sourceList.toMutableList()
//        val readOnlyCopyList = sourceList.toList()
//        copyList.add(3)
//        copyList.add(4)
//
//        println("List size: ${sourceList.size}")
//
//        println("Copy size: ${copyList.size}")
//
//        println("Read-only copy size: ${readOnlyCopyList.size}")

//        val sourceList = mutableListOf(1, 2, 3)
//        val referenceList = sourceList
//      //  referenceList.add(4)
//        sourceList.add(5)
//        println("Source size: ${referenceList.size}")


//        val numbers = listOf("one", "two", "three", "four")
//        val filterResults = mutableListOf<String>()  //destination object
//        numbers.filterTo(filterResults) { it.length > 3 }
//        numbers.filterIndexedTo(filterResults) { index, _ -> index == 0 }
//        println(filterResults) // contains results of both operations

//        val colors = listOf("red", "brown", "grey")
//        val animals = listOf("fox", "bear", "wolf")
//        val a = colors zip animals
//        println(a)
//
//        val twoAnimals = listOf("fox", "bear")
//
//        val b = colors.zip(twoAnimals)
//
//        val c = b.unzip()
//
//        println(c)


//        val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
//        println(numberSets.flatten())

//        val numbers = listOf("one", "two", "three", "four")
//
//        println(numbers)
//        println(numbers.joinToString())
//
//        val listString = StringBuffer("The list of numbers: ")
//        numbers.joinTo(listString)
//        println(listString)
        val list = (1..10000).average()
        println(list)
    }

    class Version(val major: Int, val minor: Int) : Comparable<Version> {
        override fun compareTo(other: Version): Int {
            return when {
                this.major != other.major -> this.major - other.major
                this.minor != other.minor -> this.minor - other.minor
                else -> 0
            }
        }
    }

    @Test
    fun main8() {
//        println(Version(1, 2) > Version(1, 3))
//        println(Version(2, 0) > Version(1, 5))

//        val numbers = mutableListOf("one", "two", "three", "four")::
//        numbers.sort()
//        println(numbers)
//        println(numbers.binarySearch("two"))  // 3
//        println(numbers.binarySearch("z")) // -5
//        println(numbers.binarySearch("two", 0, numbers.size))  // -3


        val productList = mutableListOf(
            Product("WebStorm", 49.0),
            Product("AppCode", 99.0),
            Product("DotTrace", 99.0),
            Product("DotTrace", 59.0),
            Product("ReSharper", 149.0)
        )

        val productList2 =  mutableListOf<Product>().apply {
            add(Product("WebStorm", 49.0))
            add(Product("AppCode", 99.0))
            add(Product("DotTrace", 99.0))
            add(Product("DotTrace", 59.0))
            add(Product("WebStorm", 49.0))
        }
        println(productList)
        println(productList2)
       // println(productList.binarySearch(Product("DotTrace", 99.0), compareBy<Product> { it.price }.thenBy { it.name }))


    }



    data class Product(val name: String, val price: Double)

}
