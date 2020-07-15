package com.team.runnershi

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Base64
import android.util.Log
import androidx.core.app.JobIntentService
import com.team.runnershi.extension.logDebug
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

class SocketService : JobIntentService() {
    private val TAG = SocketService::class.simpleName
    lateinit var socketThread: SocketThread
    private lateinit var mSocket: Socket
    private var isSocketExist = false
    private val mHost = "http://13.125.20.117:3000/matching"
    private var roomName: String = ""


    override fun onHandleWork(intent: Intent) {
        if (!isSocketExist) {
            try {
                socketConnect()
                "Socket Send Connect".logDebug(this@SocketService)
            } catch (e: URISyntaxException) {
                Log.d(
                    TAG,
                    "Socket Connect Reason: ${e.reason} (index: ${e.index}) (message:${e.message}"
                )
            }
            isSocketExist = true
        }
        "Appear on onHandleWork".logDebug(this@SocketService)
        when (intent.getStringExtra("serviceFlag")) {
            "joinRoom" -> {
                val token = intent.getStringExtra("token")
                val time = intent.getIntExtra("time", -1)
                val wantGender = intent.getIntExtra("wantGender", -1)
                val leftTime = intent.getIntExtra("leftTime", -1)
                "JoinRoom (token: $token) (time: $time) (wantGender: $wantGender) (leftTime: $leftTime) (SocketId: ${mSocket.id()})".logDebug(
                    this@SocketService
                )
                mSocket.emit("joinRoom", token, time, wantGender, leftTime)
                "Send JoinRoom".logDebug(this@SocketService)
            }
            "stopCount" -> {
                roomName = intent.getStringExtra("roomName")!!
                mSocket.emit("stopCount", roomName)
            }
            "quit" -> {
                socketDisconnect()
            }
            "stopMatching" -> {
                mSocket.emit("stopMatching", roomName)
            }
            "readyToRun" -> {
                roomName = intent.getStringExtra("roomName")!!
                mSocket.emit("readyToRun", roomName)
                ("Send Ready to Run (roomName: $roomName) (SocketId : ${mSocket.id()}").logDebug(this@SocketService)

            }
            "kmPasssed" -> {
                roomName = intent.getStringExtra("roomName")!!
                val km = intent.getIntExtra("km", -1)
                mSocket.emit("kmPassed", roomName, km)
                "Send KmPassed (roomName :$roomName) (km  : $km)".logDebug(this@SocketService)
            }
            "stopRunning" -> {
                val distance = intent.getIntExtra("distance", -1) // meter
                val time = intent.getIntExtra("time", -1) // 초
                val coordinates = intent.getSerializableExtra("coordinates") // location
                mSocket.emit("stopRunning", roomName, distance, time, coordinates)
            }
            "endRunning" -> {
                roomName = intent.getStringExtra("roomName")!!
                val distance = intent.getIntExtra("distance", -1)
                mSocket.emit("endRunning", roomName, distance)
                "Send EndRuning (roomName : $roomName) (distance : $distance)".logDebug(this@SocketService)
            }
            "runComplete" -> {
                mSocket.emit("runComplete", roomName)
            }
        }
    }

    private fun socketConnect() {
        mSocket = IO.socket(mHost)
        mSocket.on(Socket.EVENT_CONNECT, onConnect) //
        mSocket.on(Socket.EVENT_RECONNECT, onReconnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect) //
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut) //
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError) //
        mSocket.on(Socket.EVENT_ERROR, onEventError) //
        mSocket.on("start", onStart) //
        mSocket.on("joinRoom", onJoinRoom) //
        mSocket.on("roomCreated", onCreatedRoom) //
        mSocket.on("timeLeft", onTimeLeft) //
        mSocket.on("timeOver", onTimeOver) //
        mSocket.on("matched", onMatched) //
        mSocket.on("endCount", onEndCount) //
        mSocket.on("stopCount", onStopCount) //
        mSocket.on("leaveRoom", onLeaveRoom)
        mSocket.on("roomFull", onRoomFull) //
        mSocket.on("opponentInfo", onOpponentInfo) //
        mSocket.on("opponentNotReady", onOpponentNotReady) //
        mSocket.on("letsRun", onLetsRun) //
        mSocket.on("kmPassed", onKmPassed) //
        mSocket.on("endRunning", onEndRunning) //
        mSocket.on("opponentStopped", onOpponentStopped) //
        mSocket.on("stopRunning", onStopRunning) //
        mSocket.on("compareResult", onCompareResult) //
        mSocket.on(Socket.EVENT_PING, onPing) //
        mSocket.connect()
    }

    private val onStart: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onStart")
    }

    private val onReconnect: Emitter.Listener = Emitter.Listener { time ->
        Log.d(TAG, "Socket onReconnect (time: $time)")
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

    private val onConnectError: Emitter.Listener = Emitter.Listener {
        "Socket onConnectError it[0]:${it[0]}".logDebug(SocketService)
    }
    private val onEventError: Emitter.Listener = Emitter.Listener {
        "Socket AutoMatically onEventError".logDebug(this@SocketService)
    }

    private val onJoinRoom: Emitter.Listener = Emitter.Listener {
        val token = it[0].toString()
        Log.d(TAG, "Socket onJoinRoom, (token: $token)")
    }

    private val onCreatedRoom: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onRoomCreated")
        roomName = it[0].toString()
        mSocket.emit("startCount", roomName)

        Intent().also { intent ->
            intent.action = "com.team.runnershi.RESULT_ROOM_NAMe"
            intent.putExtra("roomName", roomName)
            sendBroadcast(intent)
        }
    }

    private val onTimeLeft: Emitter.Listener = Emitter.Listener {
        val leftTime = it[0] as Int
        Log.d(TAG, "Socket onTimeLeft LeftTime: $leftTime")

        Intent().also { intent ->
            intent.action = "com.team.runnershi.RESULT_LEFT_TIME"
            intent.putExtra("leftTIme", leftTime)
            sendBroadcast(intent)
        }

    }

    private val onEndCount: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onEndCount (Room Name :$roomName)")
        roomName = it[0].toString()
    }

    private val onTimeOver: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onTimeOver")
        socketDisconnect()
    }

    private val onStopCount: Emitter.Listener = Emitter.Listener {
        val leftTime = it[0] as Int
        Log.d(TAG, "Socket onStopCount (Room Name :$roomName) (leftTime: $leftTime)")
        mSocket.emit("leaveRoom", roomName)
    }
    private val onLeaveRoom: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onLeaveRoom")
        socketDisconnect()
    }

    private val onMatched: Emitter.Listener = Emitter.Listener {
        roomName = it[0].toString()
        Log.d(TAG, "Socket onMatched (Room Name :$roomName)")
        mSocket.emit("endCount", roomName)
        Log.d(TAG, "Send Socket endCount (Room Name :$roomName)")
    }
    private val onRoomFull: Emitter.Listener = Emitter.Listener {
        roomName = it[0].toString()
        Log.d(TAG, "Socket onRoomFull (roomName: $roomName)")
        mSocket.emit("opponentInfo", roomName)
        Log.d(TAG, "Send Socket opponentInfo (roomName: $roomName)")
    }

    private val onOpponentInfo: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onOpponenetInfo")
        roomName = it[0].toString()

        val strBase64UrlDecode = Base64.decode(it[1].toString(), Base64.URL_SAFE)
        val name = String(strBase64UrlDecode)
        val level = it[2] as Int
        val win = it[3] as Int
        val lose = it[4] as Int
        val image = it[5] as Int
        "(roomName: $roomName) (name: $name) (level: $level)  (win: $win) (lose: $lose) (image: $image)".logDebug(
            this@SocketService
        )

        Intent().also { intent ->
            intent.action = "com.team.runnershi.RESULT_OPPONENT_INFO"
            intent.putExtra("roomName", roomName)
            intent.putExtra("name", name)
            intent.putExtra("level", level)
            intent.putExtra("win", win)
            intent.putExtra("lose", lose)
            intent.putExtra("image", image)
            sendBroadcast(intent)
        }
    }

    private val onOpponentNotReady: Emitter.Listener = Emitter.Listener {
        "Socket onOpponentNotReady".logDebug(SocketService)
    }

    private val onLetsRun: Emitter.Listener = Emitter.Listener {
        "Socket onLetsRun (Room Name:$roomName)".logDebug(this@SocketService)
        Intent().also { intent ->
            intent.action = "com.team.runnershi.RESULT_LETS_RUN"
            sendBroadcast(intent)
        }
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

    private val onCompareResult: Emitter.Listener = Emitter.Listener {
        Log.d(TAG, "Socket onCompareResult")
        socketDisconnect()
        // todo 뷰를 finish 로 넘김
    }


    private val onPing: Emitter.Listener = Emitter.Listener {
        "Socket onPing".logDebug(this@SocketService)
        mSocket.emit(Socket.EVENT_PONG)
        "Send Pong".logDebug(this@SocketService)
    }


    override fun onStopCurrentWork(): Boolean {
        "onStopCurrentWork".logDebug(this@SocketService)
        return super.onStopCurrentWork()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SocketService is destroyed".logDebug(SocketService::class)
    }

    private fun socketDisconnect() {
        mSocket.off(Socket.EVENT_CONNECT, onConnect) //
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect) //
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut) //
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError) //
        mSocket.off(Socket.EVENT_ERROR, onEventError) //
        mSocket.off("start", onStart) //
        mSocket.off("joinRoom", onJoinRoom) //
        mSocket.off("roomCreated", onCreatedRoom) //
        mSocket.off("timeLeft", onTimeLeft) //
        mSocket.off("timeOver", onTimeOver) //
        mSocket.off("matched", onMatched) //
        mSocket.off("endCount", onEndCount) //
        mSocket.off("stopCount", onStopCount) //
        mSocket.off("leaveRoom", onLeaveRoom)
        mSocket.off("roomFull", onRoomFull) //
        mSocket.off("opponentInfo", onOpponentInfo) //
        mSocket.off("opponentNotReady", onOpponentNotReady) //
        mSocket.off("letsRun", onLetsRun) //
        mSocket.off("kmPassed", onKmPassed) //
        mSocket.off("endRunning", onEndRunning) //
        mSocket.off("opponentStopped", onOpponentStopped) //
        mSocket.off("stopRunning", onStopRunning) //
        mSocket.off("compareResult", onCompareResult) //
        mSocket.off(Socket.EVENT_PING, onPing) //
        mSocket.disconnect()
    }

    fun getSocket(): Socket {
        mSocket?.let {
            return it
        }
    }

    companion object {
        const val JOB_ID = 1001
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, SocketService::class.java, JOB_ID, work)
        }
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
