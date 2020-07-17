package com.team.runnershi.feature.signup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.team.runnershi.R

class DialSignUpLvDesc(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dial_sign_up_lv_desc)
    }

}

