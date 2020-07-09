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
import java.net.Socket

class MatchProcActivity : AppCompatActivity() {
    private val TAG = MatchProcActivity::class.simpleName
    private lateinit var mService: SocketService

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onService Disconnected")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onService Connected")
            val binder = p1 as SocketService.LocalBinder
            mService = binder.getService()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_proc)
    }

    override fun onStart() {
        super.onStart()
        Intent(this, SocketService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    val matchHandler = Handler(Looper.getMainLooper(), Handler.Callback {
        val leftTime = it.arg1
        return@Callback true
    })


}