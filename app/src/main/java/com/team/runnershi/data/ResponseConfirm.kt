package com.team.runnershi.data

data class ResponseConfirm (
    val status : Int,
    val success : Boolean,
    val message : String,
    val code : Int?,
    val description : String?
)