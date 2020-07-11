package com.team.runnershi.network

import com.example.semina_3st.data.RequestLogin
import com.example.semina_3st.data.ResponseLogin
import com.team.runnershi.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestInterface {
    @POST("user/duplicates")
    fun requestConfirm(@Body body : RequestConfirm) : Call<ResponseConfirm>
    @POST("user/register")
    fun requestRegister(@Body body : RequestRegister) : Call<ResponseRegister>
    @POST("user/login")
    fun requestLogin(@Body body : RequestLogin): Call<ResponseLogin>


    @GET("record/all")
    fun requestRecord(@Header("token") token : String) : Call<ResponseRecord>

    @GET("record/badge")
    fun requestBadge(@Header("token") token : String) : Call<ResponseBadge>

}