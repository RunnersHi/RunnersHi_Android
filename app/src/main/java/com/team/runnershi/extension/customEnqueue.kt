package com.team.runnershi.extension

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType>Call<ResponseType>.customEnqueue(
    onFailure:(Call<ResponseType>, Throwable) -> Unit,
    onResponse: (Call<ResponseType>, Response<ResponseType>) -> Unit
){
    this.enqueue(object: Callback<ResponseType>{
        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFailure(call, t)
        }
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            onResponse(call, response)
        }
    })
}