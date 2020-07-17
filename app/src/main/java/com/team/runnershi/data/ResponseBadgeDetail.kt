package com.team.runnershi.data

data class ResponseBadgeDetail(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result : AllBadgeDetailContent
)