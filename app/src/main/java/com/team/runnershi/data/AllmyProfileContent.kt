package com.team.runnershi.data

data class AllmyProfileContent(
    val user_idx : Int,
    val nickname : String,
    val level : Int,
    val gender : Int,
    val image : Int,
    val badge : MutableList<Boolean>,
    val win : Int,
    val lose : Int
)