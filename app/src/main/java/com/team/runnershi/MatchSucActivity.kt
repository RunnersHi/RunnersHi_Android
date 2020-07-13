package com.team.runnershi

import android.app.Activity
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.team.runnershi.SocketService.Companion.RESULT_LETS_RUN
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_match_suc.*

class MatchSucActivity : AppCompatActivity(), SocketServiceReceiver.Receiver {
    private lateinit var socketResultReceiver: SocketServiceReceiver
    private var roomName: String? = ""
    private var name: String? = ""
    private var level = -1
    private var gender = -1
    private var win = -1
    private var lose = -1
    private var image = -1
    private lateinit var resultHandler: Handler
    private lateinit var socketReceiver: MatchSucReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_suc)

        initServiceReceiver()
        initUi()
        wait3Minutes()
//        sendReadyToRun()
    }

    private fun initServiceReceiver() {
        resultHandler = Handler(Looper.myLooper()!!)
        socketResultReceiver = SocketServiceReceiver(resultHandler)
        socketResultReceiver.receiver = this
        "initServiceReceiver (Result Receiver: $socketResultReceiver)".logDebug(this@MatchSucActivity)

        socketReceiver = MatchSucReceiver()
        intentFilter = IntentFilter()
        intentFilter.addAction("com.team.runnershi.RESULT_LETS_RUN")
        registerReceiver(socketReceiver, intentFilter)
    }

    private fun initUi() {
        roomName = intent.getStringExtra("roomName")
        name = intent.getStringExtra("name")
        level = intent.getIntExtra("level", -1)
        gender = intent.getIntExtra("gender", -1)
        win = intent.getIntExtra("win", -1)
        lose = intent.getIntExtra("lose", -1)
        image = intent.getIntExtra("image", -1)

        tv_match_suc_nick_name.text = name

        when (level) {
            1 -> tv_match_suc_lv_data.text = "초급"
            2 -> tv_match_suc_lv_data.text = "중급"
            3 -> tv_match_suc_lv_data.text = "고급"
            else -> tv_match_suc_lv_data.text = "초급"
        }

        tv_match_suc_lv_score_data.text = "${win}승 ${lose}패"

        when (image) {
            1 -> Glide.with(this).load(R.drawable.icon_redman_shorthair)
                .into(imgv_match_suc_profile)
            2 -> Glide.with(this).load(R.drawable.icon_blueman_shorthair)
                .into(imgv_match_suc_profile)
            3 -> Glide.with(this).load(R.drawable.icon_redman_basichair)
                .into(imgv_match_suc_profile)
            4 -> Glide.with(this).load(R.drawable.icon_blueman_permhair)
                .into(imgv_match_suc_profile)
            5 -> Glide.with(this).load(R.drawable.icon_redwomen_ponytail)
                .into(imgv_match_suc_profile)
            6 -> Glide.with(this).load(R.drawable.icon_bluewomen_ponytail)
                .into(imgv_match_suc_profile)
            7 -> Glide.with(this).load(R.drawable.icon_redman_shorthair)
                .into(imgv_match_suc_profile)
            8 -> Glide.with(this).load(R.drawable.icon_redwomen_shortmhair)
                .into(imgv_match_suc_profile)
            9 -> Glide.with(this).load(R.drawable.icon_redwomen_bunhair)
                .into(imgv_match_suc_profile)
            else -> Glide.with(this).load(R.drawable.icon_redwomen_bunhair)
                .into(imgv_match_suc_profile)
        }
    }

    private fun wait3Minutes() {
        Handler(Looper.getMainLooper())
            .postDelayed(
                Runnable {
                    sendReadyToRun()
                },
                3000
            )
    }

    private fun sendReadyToRun() {
        val work = Intent()
        with(work) {
            this.putExtra("roomName", roomName)
            this.putExtra("serviceFlag", "readyToRun")
            this.putExtra("receiver", socketResultReceiver)
        }
        "MatchSucActivity (Result Reciever: $socketResultReceiver)".logDebug(this@MatchSucActivity)
        SocketService.enqueueWork(this, work)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        when (resultCode) {
            RESULT_LETS_RUN -> {
                val runtime = intent.getIntExtra("runtime", -1)
                val intent = Intent(this, CountDownActivity::class.java)
                with(intent) {
                    putExtra("roomName", roomName)
                    putExtra("name", name)
                    putExtra("level", level)
                    putExtra("image", image)
                    putExtra("win", win)
                    putExtra("lose", lose)
                    putExtra("runtime", runtime)
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(socketReceiver)
    }

    inner class MatchSucReceiver() : BroadcastReceiver() {
        override fun onReceive(contex: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.team.runnershi.RESULT_LETS_RUN" -> {
                    val runtime = intent.getIntExtra("runtime", -1)
                    Intent(this@MatchSucActivity, CountDownActivity::class.java)
                        .also { intent ->
                            intent.putExtra("roomName", roomName)
                            intent.putExtra("name", name)
                            intent.putExtra("level", level)
                            intent.putExtra("image", image)
                            intent.putExtra("win", win)
                            intent.putExtra("lose", lose)
                            intent.putExtra("runtime", runtime)
                            startActivity(intent)
                        }
                }
            }
        }

    }


}