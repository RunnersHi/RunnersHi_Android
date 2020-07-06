package com.team.runnershi.network

import com.example.semina_3st.data.RequestLogin
import com.example.semina_3st.data.ResponseLogin
import com.team.runnershi.data.RequestConfirm
import com.team.runnershi.data.RequestRegister
import com.team.runnershi.data.ResponseConfirm
import com.team.runnershi.data.ResponseRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestInterface {
    @POST("/users/duplicates")
    fun requestConfirm(@Body body : RequestConfirm) : Call<ResponseConfirm>
    @POST("/users/register")
    fun requestRegister(@Body body : RequestRegister) : Call<ResponseRegister>
    @POST("/users/login")
    fun requestLogin(@Body body : RequestLogin): Call<ResponseLogin>

}