package com.team.runnershi.extension

import android.content.Context
import android.content.Intent

fun <T> Context.newStartActivity(toClass: Class<T>){
    val intent = Intent(this, toClass)
    startActivity(intent)
}