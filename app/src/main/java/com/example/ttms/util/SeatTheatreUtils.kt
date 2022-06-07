package com.example.ttms.util

import android.util.Log
import com.example.ttms.bean.SeatData
import com.example.ttms.bean.TheatreData
import com.example.ttms.customizedView.SeatTable

object SeatTheatreUtils {

    private var firstId: Int = 0
    fun setFirstId(id: Int) {
        firstId = id
    }

    fun getSeatByRC(map: Map<Int, SeatData>, row: Int, col: Int, theatre: TheatreData): SeatData {
        val id = firstId + (row - 1) * theatre.col + col - 1
        return map[id] ?: SeatData()
    }

    fun listToMap(list: List<SeatData>): Map<Int, SeatData> {
        val map = mutableMapOf<Int, SeatData>()
        for (seatData in list) {
            map[seatData.seatId] = seatData
        }
        return map
    }

}