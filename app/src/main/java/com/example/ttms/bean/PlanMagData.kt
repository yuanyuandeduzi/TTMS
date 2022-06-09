package com.example.ttms.bean


import com.google.gson.annotations.SerializedName

data class PlanMagData(
    @SerializedName("schedule")
    var planData: List<PlanData>,
    @SerializedName("sum")
    var sum: Int
)