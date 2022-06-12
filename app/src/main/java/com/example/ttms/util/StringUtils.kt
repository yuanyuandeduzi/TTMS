package com.example.ttms.util


object StringUtils {

    //长字符转...结尾
    fun getStringByLength(content: String?, length: Int): String {
        return if (content != null) {
            if (content.length > length) {
                content.substring(0, length) + "..."
            } else {
                content
            }
        }else {
            "默认值"
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
        for (j in 0 until num) {
            result = result.plus("${list[j]}\n ")
        }
        return result.plus(list[num])
    }
}