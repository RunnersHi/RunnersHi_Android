package com.team.runnershi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.team.runnershi.SocketService.Companion.RESULT_LEFT_TIME
import com.team.runnershi.SocketService.Companion.RESULT_OPPONENT_INFO
import com.team.runnershi.SocketService.Companion.RESULT_ROOM_NAME
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
        socketResultReceiver.receiver = this
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
//                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDYxNDU0OSwiZXhwIjoxNTk0NjUwNTQ5fQ.uExmkKAL5iqKK2FZXoaTnf7eWU7juzmNNuJTYa5EZCM" // 6
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDYxNDU4MiwiZXhwIjoxNTk0NjUwNTgyfQ.zYtTFMY6PWMzdRCwlJwPx-mmYse6WRSVTy43JDipfRk" //7
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
                val roomName = resultData.getString("roomName")
                val name = resultData.getString("name")
                val level = resultData.getInt("level")
                val gender = resultData.getInt("gender")
                val win = resultData.getInt("win")
                val lose = resultData.getInt("lose")
                val image = resultData.getInt("image")


                val intent = Intent(this, MatchSucActivity::class.java)
                with(intent) {
                    this.putExtra("roomName", roomName)
                    this.putExtra("name", name)
                    this.putExtra("level", level)
                    this.putExtra("gender", gender)
                    this.putExtra("win", win)
                    this.putExtra("lose", lose)
                    this.putExtra("image", image)
                    this.putExtra("runtime", runtime)
                }
                startActivity(intent)
                finish()
            }
            RESULT_ROOM_NAME -> {
                roomName = resultData.getString("roomName")!!
            }
            else -> return
        }
    }

}