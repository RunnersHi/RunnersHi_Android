package com.example.semina_3st.data

data class ResponseLogin(
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
