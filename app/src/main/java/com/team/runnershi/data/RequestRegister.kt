package com.team.runnershi.data

data class RequestRegister (
    val id : String,
    val password : String,
    val nickname : String,
    val gender : Int,
    val level : Int,
    val log_visibility : Boolean,
    val image : Int
)
