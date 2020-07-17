package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RequestRecordRunPost(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("result")
    val result: Int, //승패여부
    @SerializedName("created_time")
    val created_time: String,
    @SerializedName("end_time")
    val end_time: String,
    @SerializedName("coordinates")
    val coordinates: MutableList<CoordinateData>
)