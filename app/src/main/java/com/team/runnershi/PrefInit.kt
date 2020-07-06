package com.team.runnershi

import android.app.Application

//참고: https://leveloper.tistory.com/133
class PrefInit : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
