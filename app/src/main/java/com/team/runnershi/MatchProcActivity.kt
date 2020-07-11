package com.team.runnershi

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_match_proc.*

class MatchProcActivity : AppCompatActivity() {
    private var runtime = -1
    private var rungender = -1
    private var leftTime = -1
    private val TAG = MatchProcActivity::class.simpleName
    private lateinit var mService: SocketService
    private val handler = Handler(Looper.getMainLooper()
    ) { message ->
        if(message.what == 1111){
            progress_match_proc.progress = message.arg1
        }
        true
    }


    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onService Disconnected")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onService Connected")
//            val binder = p1 as SocketService.LocalBinder
//            mService = binder.getService()


            val lefttime = runtime * 60
            val work = Intent().apply {
                putExtra("serviceFlag", "joinRoom")
                putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3Q4IiwicGFzc3dvcmQiOiJ0ZXN0IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDE4MTE4NiwiZXhwIjoxNTk0MjE3MTg2fQ.72wmGGKZtK4UYhz16M2GHd8pfHF8hz-BlcFOdjo_634")
                putExtra("time", lefttime)
                putExtra("wantGender",rungender)
                putExtra("leftTime",  lefttime)
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_proc)

        runtime = intent.getIntExtra("runtime", -1)
        rungender= intent.getIntExtra("rungender", -1)

    }

    override fun onStart() {
        super.onStart()
//        Intent(this, SocketService::class.java).also { intent ->
//            bindService(intent, connection, Context.BIND_AUTO_CREATE)
//        }

        val lefttime = runtime * 60
        val work = Intent()
        work.putExtra("serviceFlag", "joinRoom")
        work.putExtra(
            "token",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0MTIiLCJwYXNzd29yZCI6InRlc3R0ZXN0MTIiLCJ0b2tlbiI6InRva2VuIiwiaWF0IjoxNTk0NDUzODkxLCJleHAiOjE1OTQ0ODk4OTF9.8BTlABtaYzW14zZZy4klppvB1BSfcW57cZl1WNyX66I"
        )
        work.putExtra("time", lefttime)
        work.putExtra("wantGender", rungender)
        work.putExtra("leftTime", lefttime)

//        startService(intent)
        SocketService.enqueueWork(this, work)
    }

    val matchHandler = Handler(Looper.getMainLooper(), Handler.Callback {
        val leftTime = it.arg1
        return@Callback true
    })




}