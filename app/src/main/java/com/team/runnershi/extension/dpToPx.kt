package com.team.runnershi.extension

import android.content.res.Resources
import android.util.TypedValue

fun Int.dpToPx(resources: Resources): Int = TypedValue
    .applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), resources.displayMetrics).toInt()