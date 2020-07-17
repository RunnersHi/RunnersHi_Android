package com.team.runnershi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.team.runnershi.PrefInit.Companion.prefs
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_match_proc.*

class MatchProcActivity : AppCompatActivity() {
    private var runtime = -1
    private var rungender = -1
    private var roomName = ""
    private lateinit var socketReceiver: MatchProcReciver
    private lateinit var intentFilter: IntentFilter
    private val token = prefs.getString("token", null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_proc)

        runtime = intent.getIntExtra("runtime", -1)
        rungender = intent.getIntExtra("rungender", -1)
        socketReceiver = MatchProcReciver()
        intentFilter = IntentFilter()
        with(intentFilter){
            addAction("com.team.runnershi.RESULT_LEFT_TIME")
            addAction("com.team.runnershi.RESULT_OPPONENT_INFO")
            addAction("com.team.runnershi.RESULT_ROOM_NAME")
        }
        registerReceiver(socketReceiver, intentFilter)

    }

    override fun onStart() {
        super.onStart()
        sendJoinRoom(runtime, rungender)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(socketReceiver)
    }

    private fun sendJoinRoom(runtime: Int, rungender: Int) {
        val work = Intent()
        work.putExtra("serviceFlag", "joinRoom")
        work.putExtra(
            "token",
            token
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc0NiwiZXhwIjoxNTk0OTM0NzQ2fQ.LzMKe_o81YcxAN5akasZPXLfjM9S_t4lCInZr8JdKFE" //6
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc2OSwiZXhwIjoxNTk0OTM0NzY5fQ.8eZnV47FRSjRj9eF_-L9x_CaiH1J2C82F2yU5NW01d0"
        ) //todo toekn 쉐어드 프리퍼런스에서 가지고 오는 걸로 바꾸
        work.putExtra("time", runtime)
        work.putExtra("wantGender", rungender)
        work.putExtra("leftTime", 300)
        SocketService.enqueueWork(this, work)
    }

    inner class MatchProcReciver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.team.runnershi.RESULT_LEFT_TIME" -> {
                    val leftTimeFromServer = intent.getIntExtra("leftTIme", -1)
                    "RESULT_LEFT_TIME $leftTimeFromServer".logDebug(this@MatchProcActivity)
                    progress_match_proc.progress = 300 - leftTimeFromServer
                    if(progress_match_proc.progress == 300){
                        goMatchFail()
                    }
                }
                "com.team.runnershi.RESULT_OPPONENT_INFO" -> {
                    roomName = intent.getStringExtra("roomName")!!
                    val name = intent.getStringExtra("name")
                    val level = intent.getIntExtra("level", -1)
                    val win = intent.getIntExtra("win", -1)
                    val lose = intent.getIntExtra("lose", -1)
                    val image = intent.getIntExtra("image", -1)

                    "RESULT_OPPONENT_INFO : (roomName ${roomName}) (name: $name) (level :$level) (image: $image) (win: $win) (lose: $lose) (runtime: $runtime)".logDebug(this@MatchProcActivity)
                    val intent = Intent(this@MatchProcActivity, MatchSucActivity::class.java)
                    with(intent) {
                        putExtra("roomName", roomName)
                        putExtra("name", name)
                        putExtra("level", level)
                        putExtra("win", win)
                        putExtra("lose", lose)
                        putExtra("image", image)
                        putExtra("runtime", runtime)
                    }
                    this@MatchProcActivity.startActivity(intent)
                    this@MatchProcActivity.finish()
                }
                "com.team.runnershi.RESULT_ROOM_NAME" -> roomName =
                    intent.getStringExtra("roomName")!!

                else -> return
            }


        }

        private fun goMatchFail(){
            Intent(this@MatchProcActivity, MatchFailActivity::class.java)
                .apply {
                    putExtra("activityFlag", 1)
                    startActivity(this)
                    finish()
                }


        }

    }


}