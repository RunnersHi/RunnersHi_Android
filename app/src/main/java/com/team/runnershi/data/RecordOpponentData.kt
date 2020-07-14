package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RecordOpponentData(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("pace")
    val pace: Double
)