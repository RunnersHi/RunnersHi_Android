package com.team.runnershi.data

data class ResponseRecordRunWithme(

    val status : Int,
    val success : Boolean,
    val message : String,
    val result : RecordRunWithmeData
)