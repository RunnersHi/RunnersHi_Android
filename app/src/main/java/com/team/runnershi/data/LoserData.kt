package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class LoserData(
    @SerializedName("nickname")
    val nickname :String,
    @SerializedName("image")
    val image :Int,
    @SerializedName("win")
    val win :Int,
    @SerializedName("lose")
    val lose :Int,
    @SerializedName("year")
    val year :String,
    @SerializedName("user_idx")
    val user_idx:Int
)