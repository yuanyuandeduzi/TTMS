package com.example.ttms.util

import android.util.Log
import okhttp3.HttpUrl.Companion.toHttpUrl

object StringUtils {

    //长字符转...结尾
    fun getStringByLength(content: String, length: Int): String {
        return if (content.length > length) {
            content.substring(0, length) + "..."
        } else {
            content
        }
    }

    //集合转[]
    fun listToBracket(list: List<String>): String {
        var result = ""
        for (s in list) {
            result = result.plus("[$s] ")
        }
        return result
    }

    //分行
    fun listToBranch(list: List<String>, i: Int): String {
        var num = i
        if (num >= list.size) {
            num = list.size - 1
        }
        var result = " "
        for (i in 0 until num) {
            result = result.plus("${list[i]}\n ")
        }
        return result.plus(list[num])
    }
}