package com.team.runnershi.data

data class ResponsemyProfile(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result : AllmyProfileContent
)