package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class PlayData(
    @SerializedName("dataSource")
    var dataSource: MutableList<DataSource>,
    @SerializedName("sum")
    var sum: Int
)