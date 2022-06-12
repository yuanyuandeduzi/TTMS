package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class PlanData(
    @SerializedName("endTime")
    var endTime: String,
    @SerializedName("movieId")
    var movieId: String,
    @SerializedName("movieName")
    var movieName: String,
    @SerializedName("scheduleId")
    var scheduleId: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("studioId")
    var studioId: String,
    @SerializedName("studioName")
    var studioName: String,
    @SerializedName("ticketPrice")
    var ticketPrice: String
)