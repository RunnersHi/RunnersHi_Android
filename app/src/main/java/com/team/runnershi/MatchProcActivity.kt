package com.team.runnershi

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.team.runnershi.SocketService.Companion.LEFT_TIME
import com.team.runnershi.SocketService.Companion.OPPONENT_INFO
import kotlinx.android.synthetic.main.activity_match_proc.*

class MatchProcActivity : AppCompatActivity(), SocketServiceReceiver.Receiver {
    private var runtime = -1
    private var rungender = -1
    private val TAG = MatchProcActivity::class.simpleName
    private lateinit var mService: SocketService
    private lateinit var socketResultReceiver: SocketServiceReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_proc)

        runtime = intent.getIntExtra("runtime", -1)
        rungender = intent.getIntExtra("rungender", -1)
        socketResultReceiver = SocketServiceReceiver(Handler(Looper.getMainLooper()))
        socketResultReceiver.reciever = this
    }

    override fun onStart() {
        super.onStart()
//        Intent(this, SocketService::class.java).also { intent ->
//            bindService(intent, connection, Context.BIND_AUTO_CREATE)
//        }

        sendJoinRoom(runtime, rungender)
    }

    private fun sendJoinRoom(runtime: Int, rungender: Int) {
        val work = Intent()
        work.putExtra("serviceFlag", "joinRoom")
        work.putExtra(
            "token",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDQ3NzI0NiwiZXhwIjoxNTk0NTEzMjQ2fQ.f7XJ6UP9W-hNVPkZFv4czXfKvcYUMwg_mylRBAxl0Ms"
        )
        work.putExtra("time", runtime)
        work.putExtra("wantGender", rungender)
        work.putExtra("leftTime", 300)
        work.putExtra("receiver", socketResultReceiver)
        SocketService.enqueueWork(this, work)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        when (resultCode) {
            LEFT_TIME -> {
                val leftTimeFromServer = resultData.getInt("leftTime")
                progress_match_proc.progress = 300 - leftTimeFromServer
            }
            OPPONENT_INFO -> {
                val name = resultData.getString("name")
                val level = resultData.getInt("level")
                val gender = resultData.getInt("gender")
                val win = resultData.getInt("win")
                val lose = resultData.getInt("lose")
                val image = resultData.getInt("image")

                val intent = Intent(this, MatchSucActivity::class.java).also {
                    it.putExtra("name", name)
                    it.putExtra("gender", gender)
                    it.putExtra("win", win)
                    it.putExtra("lose", lose)
                    it.putExtra("image", image)
                    startActivity(it)
                }



            }
            else -> return
        }
    }

}