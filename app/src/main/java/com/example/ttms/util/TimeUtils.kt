package com.example.ttms.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    //毫秒值转日期
    @SuppressLint("SimpleDateFormat")
    fun msToDay(ms: String): String {
        val date = Date(ms.toLong())
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        return sdf.format(date)
    }

    //转小时时间
    @SuppressLint("SimpleDateFormat")
    fun msToHour(ms: String): String {
        val date = Date(ms.toLong())
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }

    //格式为"2020/12/22~16:00"
    @SuppressLint("SimpleDateFormat")
    fun timeToMs(time : String) : String {
        val sdf = SimpleDateFormat("yyyy/MM/dd/HH:mm")
        val date = sdf.parse(time)
        return date?.time.toString()
    }

    fun msAddMin(ms : String,min : String) : String{
        return ms.toLong().plus(min.toInt().times(60000)).toString()
    }
}