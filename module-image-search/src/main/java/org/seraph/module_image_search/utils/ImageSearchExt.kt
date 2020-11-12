package org.seraph.module_image_search.utils

import java.util.regex.Pattern

/**
解析百度加密的 objURL 地址
 */
fun String?.urlToBaiduComplie(): String {
    var res = ""
    this?.let {
        val c = arrayListOf("_z2C\$q", "_z&e3B", "AzdH3F")
        val d = mapOf(
            "w" to "a", "k" to "b", "v" to "c", "1" to "d", "j" to "e", "u" to "f",
            "2" to "g", "i" to "h", "t" to "i", "3" to "j", "h" to "k", "s" to "l",
            "4" to "m", "g" to "n", "5" to "o", "r" to "p", "q" to "q", "6" to "r",
            "f" to "s", "p" to "t", "7" to "u", "e" to "v", "o" to "w", "8" to "1",
            "d" to "2", "n" to "3", "9" to "4", "c" to "5", "m" to "6", "0" to "7",
            "b" to "8", "l" to "9", "a" to "0", "_z2C\$q" to ":", "_z&e3B" to ".",
            "AzdH3F" to "/"
        )
        var j = it
        c.forEach { cIt ->
            j = j.replace(cIt, d[cIt] ?: "")
        }
        j.forEach { jIt ->
            if (Pattern.compile("^[a-w\\d]+\$").matcher(jIt.toString()).matches()) {
                res += d[jIt.toString()]
            } else {
                res += jIt
            }
        }
    }
    return res
}