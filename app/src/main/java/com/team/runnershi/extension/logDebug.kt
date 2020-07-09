package com.team.runnershi.extension

import android.util.Log

fun String.logDebug(any: Any) {
    Log.d(any::class.java.simpleName, this)
}