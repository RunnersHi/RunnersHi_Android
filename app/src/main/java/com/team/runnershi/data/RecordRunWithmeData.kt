package com.team.runnershi.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordRunWithmeData (
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("level")
    val level : Int,
    @SerializedName("gender")
    val gender : Int,
    @SerializedName("win")
    val win : Int,
    @SerializedName("lose")
    val lose : Int,
    @SerializedName("image")
    val image : Int,
    @SerializedName("pace_minute")
    val pace_minute : Int,
    @SerializedName("pace_second")
    val pace_second : Int,
    @SerializedName("distance")
    val distance : Int,
    @SerializedName("time")
    val time :String,
    @SerializedName("isDummy")
    val isDummy : Boolean
): Parcelable