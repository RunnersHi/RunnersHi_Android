package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_run.*

class RunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

        initUi()
    }

    private fun initUi(){
        Glide.with(this).load(R.drawable.icon_unlock).into(btn_run_lock)
    }
}