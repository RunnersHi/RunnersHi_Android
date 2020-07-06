package com.team.runnershi.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun<ResponseType> Call<ResponseType>.customEnqueue( //매개변수로 넘겨줄 함수들
    onFail : () -> Unit = { Log.d("com/team/runnershi/network","통신에 실패했습니다.")},
    onSuccess : (ResponseType) -> Unit,
    onError : () -> Unit
) {
    this.enqueue(object : Callback<ResponseType>{
        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFail()
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            response.body()?.let{//body가 있다면(not null) statuscode가 200-300사이
                onSuccess(it) // 통신 결과를 전달해 줍니다
            } ?: onError // body()가 null값 -> statusCode가 200-300이 아닌경우.아예 오류
        }
    })
}