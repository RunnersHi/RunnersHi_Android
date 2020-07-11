package com.team.runnershi

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_run.*


class RunActivity : AppCompatActivity() {
    private lateinit var runSelModel: RunSetViewModel
    private lateinit var thumView: View
    private val runSetViewModel = RunSetViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)
        initUi()
    }

    private fun initUi() {
        Glide.with(this).load(R.drawable.icon_unlock).into(btn_run_lock)
        runSelModel = RunSetViewModel()



        seekbar_run_record.setOnTouchListener { _, _ -> true }
        seekbar_run_record.max = runSelModel.runTime.value ?: 30


        Thread(Runnable {
            for (i in 1 until seekbar_run_record.max) {
                Thread.sleep(1000)
                seekbar_run_record.progress += 1

            }

        }).start()





    }


}