package com.example.logintest.network

interface IResponse<T> {

    fun getMData() : T?
    fun getMessage() : String

    fun getMCode() : Int
    fun isSuccessful() : Boolean

}