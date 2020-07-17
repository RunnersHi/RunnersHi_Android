package com.team.runnershi.network

import com.example.semina_3st.data.RequestLogin
import com.example.semina_3st.data.ResponseLogin
import com.team.runnershi.data.*
import retrofit2.Call
import retrofit2.http.*


interface RequestInterface {
    @POST("user/duplicates")
    fun requestConfirm(@Body body : RequestConfirm) : Call<ResponseConfirm>
    @POST("user/register")
    fun requestRegister(@Body body : RequestRegister) : Call<ResponseRegister>
    @POST("user/login")
    fun requestLogin(@Body body : RequestLogin): Call<ResponseLogin>

    //랭킹화면
    @GET("ranking/runner")
    fun requestRunner():Call<ResponseRunner>
    @GET("ranking/winner")
    fun requestWinner():Call<ResponseWinner>
    @GET("ranking/loser")
    fun requestLoser():Call<ResponseLoser>

    @GET("/record/detail/{run_idx}")
    fun requestRecordDetail(@Header("token") token :String, @Path("run_idx") run_idx :Int):Call<ResponseRecordDetail>
    @GET("/record/run/{run_idx}")
    fun requestRecordRun(@Header("token") token :String, @Path("run_idx") run_idx :Int):Call<ResponseRecordRun>
    @GET("/record/opponent/{game_idx}")
    fun requestRecordOpponent(@Header("token") token :String, @Path("game_idx") game_idx :Int):Call<ResponseRecordOpponent>

    //세곤
    @POST("record/match/opponent")
    fun requestDummyData(@Body body : RequestDummyData) : Call<ResponseDummyData>


    @GET("record/all")
    fun requestRecord(@Header("token") token : String) : Call<ResponseRecord>

    @GET("record/badge")
    fun requestBadge(@Header("token") token : String) : Call<ResponseBadge>

    @GET("user/myprofile")
    fun requestmyProfile(@Header("token") token : String) : Call<ResponsemyProfile>

    @GET("record/recent")
    fun requestRecordRecent(@Header("token") token : String) : Call<ResponseRecordRecent>
}