package com.team.runnershi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer{
    var retrofit = Retrofit.Builder()
        .baseUrl("http://13.125.20.117:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}