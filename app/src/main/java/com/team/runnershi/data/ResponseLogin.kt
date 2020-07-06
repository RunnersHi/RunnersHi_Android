package com.example.semina_3st.data

data class ResponseLogin(
    val status : Int,
    val success : Boolean,
    val message : String,
    val token : String
)
