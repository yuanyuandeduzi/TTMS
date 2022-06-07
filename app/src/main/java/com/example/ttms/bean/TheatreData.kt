package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class TheatreData(
    @SerializedName("col")
    var col: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("row")
    var row: Int,
    @SerializedName("status")
    var status: Int
) {
    constructor() : this(0,0,"",0,0)
}

