package com.team.runnershi

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.core.app.JobIntentService
import com.team.runnershi.extension.logDebug
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

class SocketService() : JobIntentService() {

    companion object{
        const val JOB_ID = 1001
        fun enqueueWork(context: Context, work: Intent){
            enqueueWork(context, SocketService::class.java, JOB_ID, work)
        }
    }

    private val TAG = SocketService::class.simpleName
//    private val socketBinder = LocalBinder()
    lateinit var socketThread: SocketThread
    private lateinit var mSocket: Socket
    private var isSocketExist = false
    private val mHost = "http://13.125.20.117:3000/matching"
    private val roomName: String = ""
    private val testtoken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3Q4IiwicGFzc3dvcmQiOiJ0ZXN0IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDE4MTE4NiwiZXhwIjoxNTk0MjE3MTg2fQ.72wmGGKZtK4UYhz16M2GHd8pfHF8hz-BlcFOdjo_634"
    private lateinit var runSetViewModel: RunSetViewModel
    private lateinit var handler: Handler

//    inner class LocalBinder : Binder() {
//        fun getService(): SocketService = this@SocketService
//    }


//    override fun onBind(intent: Intent): IBinder? {
////        return socketBinder
//        return null
//    }



    override fun onHandleWork(intent: Intent) {
        if(!isSocketExist){
//            socketThread = SocketThread()
//            socketThread.start()
            try {
                socketConnect()
            } catch (e: URISyntaxException) {
                Log.d(
                    TAG,
                    "Socket Connect Reason: ${e.reason} (index: ${e.index}) (message:${e.message}"
                )
            }
            handler = Handler(Looper.getMainLooper())
            isSocketExist = true
        }
        "onHandleWork 들어왔다.".logDebug(this@SocketService )
        when (intent.getStringExtra("serviceFlag")) {
            "joinRoom" -> {
                val token = intent.getStringExtra("token")
                val time = intent.getIntExtra("time", -1)
                val wantGender = intent.getIntExtra("wantGender", -1)
                val leftTime = intent.getIntExtra("leftTime", -1)
                "(token: $token) (time: $time) (wantGender: $wantGender) (leftTime: $leftTime)".logDebug(this@SocketService)
                mSocket.emit("joinRoom", token, time, wantGender, leftTime)
            }
            "stopMatching" -> {
                mSocket.emit("stopMatching", roomName)
            }
            "matchComplete" -> {
                mSocket.emit("matchComplete", roomName)
            }
            "kmPasssed" -> {
                val km = intent.getIntExtra("km", -1)
                mSocket.emit("kmPassed", roomName, km)
            }
            "stopRunning" -> {
                val distance = intent.getIntExtra("distance", -1) // meter
                val time = intent.getIntExtra("time", -1) // 초
                val coordinates = intent.getSerializableExtra("coordinates") // location
                mSocket.emit("stopRunning", roomName, distance, time, coordinates)
            }
            "endRunning" -> {
                val distance = intent.getIntExtra("distance", -1) // meter
                val time = intent.getIntExtra("time", -1) // 초
                val coordinates = intent.getSerializableExtra("coordinates") // location
                mSocket.emit("endRunning", roomName, distance, time, coordinates)
            }
            "runComplete" -> {
                mSocket.emit("runComplete", roomName)
            }
        }
    }

    private fun socketConnect() {
        mSocket = IO.socket(mHost)
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut)
        mSocket.on("start", onStart)
        mSocket.on("joinRoom", onJoinRoom)
        mSocket.on("roomCreated", onRoomCreated)
        mSocket.on("timeLeft", onTimeLeft)
        mSocket.on("timeOver", onTimeOver)
        mSocket.on("leaveRoom", onLeaveRoom)
        mSocket.on("stopMatching", onStopMatching)
        mSocket.on("matched", onMatched)
        mSocket.on("matchWaiting", onMatchWaiting)
        mSocket.on("matchComplete", onMatchComplete)
        mSocket.on("kmPassed", onKmPassed)
        mSocket.on("opponentStopped", onOpponentStopped)
        mSocket.on("stopRunning", onStopRunning)
        mSocket.on("endRunning", onEndRunning)
        mSocket.on("runWaiting", onRunWaiting)
        mSocket.on("runComplete", onRunComplete)
        mSocket.connect()
    }

    private val onStart: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onStart")
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onConnect")
        Log.d(TAG, "Connect Time = ${SystemClock.elapsedRealtime()}")
    }
    private val onDisconnect: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onDisconnect")
        Log.d(TAG, "Connect Time = ${SystemClock.elapsedRealtime()}")
    }
    private val onConnctTimeOut: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onConnectTimeOut")
    }

    private val onJoinRoom: Emitter.Listener = Emitter.Listener {
        val token = it[0].toString()
        Log.d(TAG, "Socket onJoinRoom, (token: $token)")
    }

    private val onRoomCreated: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onRoomCreated")
        val roomName = it[0] to String
        mSocket.emit("startCount", roomName)
    }

    private val onTimeLeft: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onTimeLeft")
        val timeLeft = it[0] as Int
        val message = Message()
            .apply {
                what = 1111
                arg1 = timeLeft
            }
        handler.sendMessage(message)
    }

    private val onTimeOver: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onTimeOver")
        val roomName = it[0] to String
        mSocket.emit("leaveRoom", roomName)
    }

    private val onLeaveRoom: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onLeaveRoom = LeaveConfirm")
        socketDisconnect()
    }

    private fun socketDisconnect() {
        mSocket.off(Socket.EVENT_CONNECT, onConnect)
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut)
        mSocket.off("start", onStart)
        mSocket.off("joinRoom", onJoinRoom)
        mSocket.off("roomCreated", onRoomCreated)
        mSocket.off("timeLeft", onTimeLeft)
        mSocket.off("timeOver", onTimeOver)
        mSocket.off("leaveRoom", onLeaveRoom)
        mSocket.off("stopMatching", onStopMatching)
        mSocket.off("matched", onMatched)
        mSocket.off("matchWaiting", onMatchWaiting)
        mSocket.off("matchComplete", onMatchComplete)
        mSocket.off("kmPassed", onKmPassed)
        mSocket.off("opponenetStopped", onOpponentStopped)
        mSocket.off("stopRunning", onStopRunning)
        mSocket.off("endRunning", onEndRunning)
        mSocket.off("runWaiting", onRunWaiting)
        mSocket.off("runComplete", onRunComplete)
        mSocket.disconnect()
    }

    private val onStopMatching: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onStopMatching")
        val roomName = it[0] to String
        mSocket.emit("leaveRoom", roomName)
    }

    private val onMatched: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onMatched")
        val opponentName = it[0].toString()
        val opponentLevel = it[0] as Int // 1, 2, 3
        val opponentGender = it[0] as Int
        val opponentWins = it[0] as Int
        val opponentLoses = it[0] as Int
        val opponentImg = it[0] as Int
        // todo 액티비티에 정보 보내기 -> 액티비티에서 뷰 넘겨야함
    }

    private val onMatchWaiting: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onMatchWaiting")
    }

    private val onMatchComplete: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onMatchComplete")
        // todo 액티비티 카운트 다운으로 넘기기
    }

    private val onKmPassed: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onKmPassed")
        // todo 음성 알림 보내기 = TTS
    }

    private val onStopRunning: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onStopRunning")
        mSocket.emit("leaveRoom", roomName)
    }

    private val onOpponentStopped: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onOpponentStopped")
    }

    private val onEndRunning: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onEndRunning")
        // todo 뷰를 finish 로 넘김
    }

    private val onRunWaiting: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onRunWaiting")
        // todo 뷰를 finish 로 넘김
    }

    private val onRunComplete: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onRunComplete")
        socketDisconnect()
        // todo 버튼 활성화 시키
    }



    override fun onStopCurrentWork(): Boolean {
        "onStopCurrentWork".logDebug(this@SocketService)
        return super.onStopCurrentWork()
    }
    override fun onDestroy() {
        super.onDestroy()
        "SocketService is destroyed".logDebug(SocketService::class)
    }



    inner class SocketThread : Thread() {
        override fun run() {
            super.run()
            try {
//                socketConnect()
            } catch (e: URISyntaxException) {
                Log.d(
                    TAG,
                    "Socket Connect Reason: ${e.reason} (index: ${e.index}) (message:${e.message}"
                )
            }
        }

    }
}
