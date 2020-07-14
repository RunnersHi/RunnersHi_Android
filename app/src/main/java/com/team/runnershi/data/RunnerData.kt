package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RunnerData(
    @SerializedName("nickname")
    val nickname :String,
    @SerializedName("image")
    val image :Int,
    @SerializedName("distance_sum")
    val sum :Int,
    @SerializedName("year")
    val year :String,
    @SerializedName("user_idx")
    val user_idx:Int
)