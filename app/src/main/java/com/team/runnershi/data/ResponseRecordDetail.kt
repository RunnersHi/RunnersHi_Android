package com.team.runnershi.data

data class ResponseRecordDetail(
    val status : Int,
    val success : Boolean,
    val message : String,
    val result: RecordDetailData
)