package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class AllRecordContent (
    @SerializedName("date")
    val created_time : String,
    @SerializedName("distance")
    val distance : Int,
    @SerializedName("time")
    val time : String,
    @SerializedName("run_idx")
    val run_idx : Int,
    @SerializedName("result")
    val result : Int,
    @SerializedName("game_idx")
    val gmae_idx : Int
)
