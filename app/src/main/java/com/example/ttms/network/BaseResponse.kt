package com.example.logintest.network

import com.google.gson.annotations.SerializedName
import java.util.*

class BaseResponse<T> : IResponse<T> {

    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("data")
    var data: T? = null

    @SerializedName("status")
    var code: String? = null

    override fun getMData(): T? {
        return data
    }

    override fun getMessage(): String {
        return msg ?: "失败"
    }

    override fun getMCode(): Int {
        return code?.toInt() ?: 0
    }

    override fun isSuccessful(): Boolean {
        return (code?.toInt() ?: 0) == 0
    }

    override fun toString(): String {
        return "BaseResponse(msg=$msg, data=$data, code=$code)"
    }


}