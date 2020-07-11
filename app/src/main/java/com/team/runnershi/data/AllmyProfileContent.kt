package com.team.runnershi.data

data class AllmyProfileContent(
    val user_idx : Int,
    val nickname : String,
    val gender : Int,
    val image : Int,
    val badge : MyProfileBadge,
    val win : Int,
    val lose : Int
)