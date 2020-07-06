package com.team.runnershi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer{
    var retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}