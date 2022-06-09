package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class PlanData(
    @SerializedName("endTime")
    var endTime: String,
    @SerializedName("movieId")
    var movieId: Int,
    @SerializedName("movieName")
    var movieName: String,
    @SerializedName("scheduleId")
    var scheduleId: Int,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("studioId")
    var studioId: Int,
    @SerializedName("studioName")
    var studioName: String,
    @SerializedName("ticketPrice")
    var ticketPrice: Int
)