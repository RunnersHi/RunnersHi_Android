package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RecordDetailData(
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int,
    @SerializedName("start_time")
    val start_time: String,
    @SerializedName("end_time")
    val end_time: String,
    @SerializedName("coordinate")
    val coordinate: List<CoordinateData>
)