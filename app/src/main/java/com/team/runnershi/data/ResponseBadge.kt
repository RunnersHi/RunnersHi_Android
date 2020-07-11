package com.team.runnershi.data

data class ResponseBadge(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result : AllBadgeContent

)