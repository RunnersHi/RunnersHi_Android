package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class AllRecordRecentContent(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("pace_minute")
    val paceMinute: Int,
    @SerializedName("pace_second")
    val paceSecond: Int,
    @SerializedName("image")
    val image: Int,
    @SerializedName("result")
    val result: Int,
    @SerializedName("created_time")
    val createdTime: String
)