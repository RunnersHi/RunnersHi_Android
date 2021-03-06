package com.team.runnershi.data

import com.google.gson.annotations.SerializedName

data class CoordinateData(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)