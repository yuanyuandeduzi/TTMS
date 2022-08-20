package com.example.logintest.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object UploadUtil {
    private val baseUrl = "http://101.201.78.192:9999/ttms/"
    //private var token: String =
    //    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJSb2xlIjozLCJpc3MiOiJhdXRoMCIsImlkIjoxLCJleHAiOjE2NTUwNDU0OTR9.-Z7BP5bpIvwp29LC-Q02TrKTbd0IDYjCPHB4CDVymBI1654354294611647187"

    private var token: String = ""


    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "token", token
            )
            .build()
        chain.proceed(request)
    }.build()

    private val okHttpClickService = OkHttpClient.Builder().addInterceptor(Interceptor {
        val proceed = it.proceed(it.request())
        token = proceed.header("token", " ").toString()
        proceed
    }).build()

    val loginService: ApiService by lazy {
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClickService)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    val postService: ApiService by lazy {
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

}