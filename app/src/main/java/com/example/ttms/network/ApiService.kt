package com.example.logintest.network

import com.example.ttms.bean.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("staff/login")
    suspend fun loginPostCall(@Body body: RequestBody): BaseResponse<String>


    @GET("studio/query-seats")
    suspend fun getSeatsGetCall(@Query("studioId") id: String): BaseResponse<List<SeatData>>

    @GET("studio/query-list")
    suspend fun getTheatreMessageGetCall(): BaseResponse<List<TheatreData>>

    @POST("studio/delete")
    suspend fun deleteTheatreByIdPostCall(@Body body: RequestBody): BaseResponse<Boolean>

    @POST("studio/insert")
    suspend fun insertTheatrePostCall(@Body body: RequestBody): BaseResponse<Boolean>

    @POST("studio/modify")
    suspend fun alterTheatrePostCall(@Body body: RequestBody): BaseResponse<Boolean>

    @POST("studio/seat/modified")
    suspend fun alterSeatBySeatPostCall(@Body body: RequestBody) : BaseResponse<Boolean>

    @GET("movie/getList")
    suspend fun getPlayMessageGetCall(@QueryMap content : Map<String,String>) : BaseResponse<PlayData>

    @GET("schedule/allQuery")
    suspend fun getPlanMessageGetCall(@QueryMap content : Map<String,String>) : BaseResponse<PlanMagData>

    @POST("schedule/add")
    suspend fun uploadPlanPostCall(@Body body: RequestBody) : BaseResponse<Boolean>
}