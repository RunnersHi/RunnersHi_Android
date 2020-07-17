package com.team.runnershi.finishrun

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.team.runnershi.FinishRunViewHolder
import com.team.runnershi.R
import com.team.runnershi.SocketService
import com.team.runnershi.result.ResultActivity
import kotlinx.android.synthetic.main.activity_finish_run.*

class FinishRunActivity : AppCompatActivity() {
    private lateinit var finishRunViewHolder: FinishRunViewHolder
    private lateinit var socketRecevier: FinishRunReceiver
    private var gameIdx = -1
    private var runIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_run)

        initButton()
        initSocketReceiver()
        sendComapreResult()
    }

    private fun initButton() {
        finishRunViewHolder = ViewModelProvider(this).get(FinishRunViewHolder::class.java)
            .apply {
                lvIfComeResult.observe(this@FinishRunActivity, Observer {
                    btn_finishrun_confirm.isClickable = it
                    if (it) {
                        btn_finishrun_confirm.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.bg_finishrun_on, null)
                    } else {
                        btn_finishrun_confirm.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.lightgrey_btn_background,
                                null
                            )
                    }
                })
            }
        btn_finishrun_confirm.setOnClickListener {
            Intent(
                this@FinishRunActivity,
                ResultActivity::class.java
            ).also { intent ->
                intent.putExtra("gameIdx", gameIdx)
                intent.putExtra("runIdx", runIdx)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initSocketReceiver() {
        socketRecevier = FinishRunReceiver()
        val intentFilter = IntentFilter().apply {
            addAction("com.team.runnershi.RESULT_COMPARE")
        }
        registerReceiver(socketRecevier, intentFilter)
    }

    private fun sendComapreResult() {
        val roomName = intent.getStringExtra("roomName")
        val distance = intent.getIntExtra("distance", -1)
        val runtime = intent.getIntExtra("runtime", -1)
        val coordinates  = intent.getSerializableExtra("coordinates")
        val createdTime = intent.getStringExtra("createdTime")
        val endTime = intent.getStringExtra("endTime")

        val work = Intent()
        with(work) {
            putExtra("serviceFlag", "compareResult")
            putExtra("roomName", roomName)
            putExtra("distance", distance)
            putExtra("runtime", runtime)
            putExtra("coordinates", coordinates)
            putExtra("createdTime", createdTime)
            putExtra("endTime", endTime)
        }
        SocketService.enqueueWork(this, work)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(socketRecevier)
    }

    inner class FinishRunReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.team.runnershi.RESULT_COMPARE" -> {
                    gameIdx = intent.getIntExtra("gameIdx", -1)
                    runIdx = intent.getIntExtra("runIdx", -1)
                    finishRunViewHolder.checkComeResult(gameIdx, runIdx)
                }
            }
        }
    }
}