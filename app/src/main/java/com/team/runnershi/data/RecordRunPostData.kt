package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class RecordRunPostData(
    @SerializedName("run_idx")
    val run_idx: Int,
    @SerializedName("game_idx")
    val game_idx: Int
)