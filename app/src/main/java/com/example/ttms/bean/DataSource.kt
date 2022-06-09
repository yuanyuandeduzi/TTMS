package com.example.ttms.bean


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataSource(
    @SerializedName("actor")
    var actor: List<String>,
    @SerializedName("area")
    var area: List<String>,
    @SerializedName("cover")
    var cover: String,
    @SerializedName("filmlen")
    var filmlen: Int,
    @SerializedName("introduction")
    var introduction: String,
    @SerializedName("language")
    var language: List<String>,
    @SerializedName("mid")
    var mid: Int,
    @SerializedName("rate")
    var rate: Double,
    @SerializedName("releaseDate")
    var releaseDate: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: List<String>
) : Serializable