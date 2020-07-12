package com.team.runnershi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.team.runnershi.SocketService.Companion.RESULT_LEFT_TIME
import com.team.runnershi.SocketService.Companion.RESULT_OPPONENT_INFO
import com.team.runnershi.SocketService.Companion.RESULT_ROOM_NAME
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_match_proc.*

class MatchProcActivity : AppCompatActivity(), SocketServiceReceiver.Receiver {
    private var runtime = -1
    private var rungender = -1
    private lateinit var socketResultReceiver: SocketServiceReceiver
    private var roomName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_proc)

        runtime = intent.getIntExtra("runtime", -1)
        rungender = intent.getIntExtra("rungender", -1)
        socketResultReceiver = SocketServiceReceiver(Handler(Looper.myLooper()!!))
        socketResultReceiver.reciever = this
    }

    override fun onStart() {
        super.onStart()
        sendJoinRoom(runtime, rungender)
    }

    private fun sendJoinRoom(runtime: Int, rungender: Int) {
        val work = Intent()
        work.putExtra("serviceFlag", "joinRoom")
        work.putExtra(
            "token",
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDU3Mjc4NywiZXhwIjoxNTk0NjA4Nzg3fQ.RoDk0q4f_eHDdwYm2laYNkr6SlLzJTAnrs-jgqMvwrE" // 7
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDU3Mjc3MSwiZXhwIjoxNTk0NjA4NzcxfQ.FeHNyuOozUdtEm4HUQXsQk5JUTyDNHArfWSQ8uR34gM" // 6
        )
        work.putExtra("time", runtime)
        work.putExtra("wantGender", rungender)
        work.putExtra("leftTime", 300)
        work.putExtra("receiver", socketResultReceiver)
        SocketService.enqueueWork(this, work)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        when (resultCode) {
            RESULT_LEFT_TIME -> {
                val leftTimeFromServer = resultData.getInt("leftTime")
                progress_match_proc.progress = 300 - leftTimeFromServer
            }

            RESULT_OPPONENT_INFO -> {
                val name = resultData.getString("name")
                val level = resultData.getInt("level")
                val gender = resultData.getInt("gender")
                val win = resultData.getInt("win")
                val lose = resultData.getInt("lose")
                val image = resultData.getInt("image")


                val intent = Intent(this, MatchSucActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("level", level)
                intent.putExtra("gender", gender)
                intent.putExtra("win", win)
                intent.putExtra("lose", lose)
                intent.putExtra("image", image)
                intent.putExtra("runtime", runtime)
                startActivity(intent)
                finish()
            }
            RESULT_ROOM_NAME ->{
                roomName = resultData.getString("roomName")!!
            }
            else -> return
        }
    }

}