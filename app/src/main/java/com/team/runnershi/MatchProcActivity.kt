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
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_match_proc.*

class MatchProcActivity : AppCompatActivity() {
    private var runtime = -1
    private var rungender = -1
    private var roomName = ""
    private lateinit var socketReceiver: MatchProcReciver
    private lateinit var intentFilter: IntentFilter

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
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDcwNTI0NiwiZXhwIjoxNTk0NzQxMjQ2fQ.cW-a_QUgXDh-XpOK12Zq946GoRSRlAegdiEFBZc40Zw"// 6 // pixel
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDcwMTY4NiwiZXhwIjoxNTk0NzM3Njg2fQ.vjMq7B_XouJMJXSXqPQv3vpXZugHAx_zdgfukxAaxnA" // 7
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImJlZW56aW5vIiwicGFzc3dvcmQiOiJiZWVuemlubyIsInRva2VuIjoidG9rZW4iLCJpYXQiOjE1OTQ3MTE0NTYsImV4cCI6MTU5NDc0NzQ1Nn0.i5iGYgX7ut-zn0duyApY9FapvukL8-MUf4Q21p8MeQg" //빈지노
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0MjEiLCJwYXNzd29yZCI6InRlc3R0ZXN0MjEiLCJ0b2tlbiI6InRva2VuIiwiaWF0IjoxNTk0NzEyMTUxLCJleHAiOjE1OTQ3NDgxNTF9.2VCPVnzmzXUhhRItSpfrj4qYda0L9WTaubezHH5dEh4"
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0MTkiLCJwYXNzd29yZCI6InRlc3R0ZXN0MTkiLCJ0b2tlbiI6InRva2VuIiwiaWF0IjoxNTk0NzEyNDM2LCJleHAiOjE1OTQ3NDg0MzZ9.T-IL605-WLWgFkaU2wI1T37_ccfH1JQZD-CJ-Riq9WA"
        )
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

    }


}