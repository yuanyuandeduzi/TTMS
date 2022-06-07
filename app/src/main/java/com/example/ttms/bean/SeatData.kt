package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class SeatData(
    @SerializedName("col")
    var col: Int,
    @SerializedName("row")
    var row: Int,
    @SerializedName("seatId")
    var seatId: Int,
    @SerializedName("status")
    var status: Int,
    @SerializedName("studioId")
    var studioId: Int
) {
    constructor() : this(10,10,3000,1,11)
}