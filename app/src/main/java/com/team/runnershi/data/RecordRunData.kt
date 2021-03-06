package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RecordRunData(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("pace_minute")
    val paceMinute: Int,
    @SerializedName("pace_second")
    val paceSecond: Int,
    @SerializedName("result")
    val result: Int //승패여부
)