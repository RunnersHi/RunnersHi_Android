package com.team.runnershi.data

data class ResponseRegister(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result : SomeData,
    val code : Int?,
    val description : String?
)

data class SomeData(
    val token : String
)