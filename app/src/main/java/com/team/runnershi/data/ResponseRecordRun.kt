package com.team.runnershi.data

data class ResponseRecordRun(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result: RecordRunData
)