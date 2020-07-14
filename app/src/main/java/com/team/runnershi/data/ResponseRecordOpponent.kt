package com.team.runnershi.data

data class ResponseRecordOpponent(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result: RecordOpponentData
)