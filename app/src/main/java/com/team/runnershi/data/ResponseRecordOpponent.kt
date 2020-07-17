package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class ResponseRecordOpponent(
    @SerializedName("status")
    val status : Int,
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("result")
    val result: RecordOpponentData
)