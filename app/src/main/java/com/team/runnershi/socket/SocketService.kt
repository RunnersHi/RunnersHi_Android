package com.team.runnershi.socket

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Base64
import android.util.Log
import androidx.core.app.JobIntentService
import com.naver.maps.geometry.LatLng
import com.team.runnershi.extension.logDebug
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class SocketService : JobIntentService() {
    private val TAG = SocketService::class.simpleName
    private var mSocket: Socket = SingleSocket.getInstance(this@SocketService)
    private var isSocketExist = false
    private val mHost = "http://13.125.20.117:3000/matching"
    private var roomName: String = ""


    override fun onHandleWork(intent: Intent) {
        if (!isSocketExist) {
            try {
//                socketConnect()
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
                SingleSocket.socketDisconnect()
            }
            "stopMatching" -> {
                mSocket.emit("stopMatching", roomName)
            }
            "readyToRun" -> {
                roomName = intent.getStringExtra("roomName")!!
                mSocket.emit("readyToRun", roomName)
                ("Send Ready to Run (roomName: $roomName) (SocketId : ${mSocket.id()}").logDebug(
                    this@SocketService
                )

            }
            "kmPassed" -> {
                roomName = intent.getStringExtra("roomName")!!
                val km = intent.getIntExtra("km", -1)
//                mSocket.emit("kmPassed", roomName, km)
                // todo kmpassed 안보냄
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
                "Send RunComplete (roomName: $roomName)".logDebug(this@SocketService)
            }
            "compareResult" -> {
                val roomName = intent.getStringExtra("roomName")
                val distance = intent.getIntExtra("distance", -1)
                val runtime = intent.getIntExtra("runtime", -1)
                val coordinates = intent.getSerializableExtra("coordinates")
                val coordsArr = getCoorsJSONArr(coordinates as ArrayList<LatLng>)
                val createdTime = intent.getStringExtra("createdTime")
                val endTime = intent.getStringExtra("endTime")

                mSocket.emit(
                    "compareResult",
                    roomName,
                    distance,
                    runtime,
                    coordsArr,
                    createdTime,
                    endTime
                )

                "Send Compare Result (roomName: $roomName) (distance: $distance) (runtime: $runtime)".logDebug(
                    this@SocketService
                )
                "Send Compare Result  (coordinates: $coordinates) (createdTime: $createdTime) (endTime: $endTime)".logDebug(
                    this@SocketService
                )
            }
        }
    }

    fun getCoorsJSONArr(coordsArr: ArrayList<LatLng>): JSONArray {
        return JSONArray(coordsArr.map {
            try{
                JSONObject().apply {
                    this.put("latitude", it.latitude)
                    this.put("longitude", it.longitude)
                }
            }catch (e: JSONException){e.printStackTrace()}
        })
//        for(coords in coordsArr){
//            try{
//                val jsonObj = JSONObject().apply {
//                    this.put("latitude", coords.latitude)
//                    this.put("longitude", coords.longitude)
//                }
//                jsonArr.put(jsonObj)
//            }catch (e: JSONException){e.printStackTrace()}
//        }
//        return jsonArr
    }
//
//    private fun socketConnect() {
//        mSocket = IO.socket(mHost)
//        mSocket.on(Socket.EVENT_CONNECT, onConnect) //
//        mSocket.on(Socket.EVENT_RECONNECT, onReconnect)
//        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect) //
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut) //
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError) //
//        mSocket.on(Socket.EVENT_ERROR, onEventError) //
//        mSocket.on("start", onStart) //
//        mSocket.on("joinRoom", onJoinRoom) //
//        mSocket.on("roomCreated", onCreatedRoom) //
//        mSocket.on("timeLeft", onTimeLeft) //
//        mSocket.on("timeOver", onTimeOver) //
//        mSocket.on("matched", onMatched) //
//        mSocket.on("endCount", onEndCount) //
//        mSocket.on("stopCount", onStopCount) //
//        mSocket.on("leaveRoom", onLeaveRoom)
//        mSocket.on("roomFull", onRoomFull) //
//        mSocket.on("opponentInfo", onOpponentInfo) //
//        mSocket.on("opponentNotReady", onOpponentNotReady) //
//        mSocket.on("letsRun", onLetsRun) //
//        mSocket.on("kmPassed", onKmPassed) //
//        mSocket.on("endRunning", onEndRunning) //
//        mSocket.on("opponentStopped", onOpponentStopped) //
//        mSocket.on("stopRunning", onStopRunning) //
//        mSocket.on("compareResult", onCompareResult) //
//        mSocket.on(Socket.EVENT_PING, onPing) //
//        mSocket.connect()
//    }
//
//
//
//
//    private fun socketDisconnect() {
//        mSocket.off(Socket.EVENT_CONNECT, onConnect) //
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect) //
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnctTimeOut) //
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError) //
//        mSocket.off(Socket.EVENT_ERROR, onEventError) //
//        mSocket.off("start", onStart) //
//        mSocket.off("joinRoom", onJoinRoom) //
//        mSocket.off("roomCreated", onCreatedRoom) //
//        mSocket.off("timeLeft", onTimeLeft) //
//        mSocket.off("timeOver", onTimeOver) //
//        mSocket.off("matched", onMatched) //
//        mSocket.off("endCount", onEndCount) //
//        mSocket.off("stopCount", onStopCount) //
//        mSocket.off("leaveRoom", onLeaveRoom)
//        mSocket.off("roomFull", onRoomFull) //
//        mSocket.off("opponentInfo", onOpponentInfo) //
//        mSocket.off("opponentNotReady", onOpponentNotReady) //
//        mSocket.off("letsRun", onLetsRun) //
//        mSocket.off("kmPassed", onKmPassed) //
//        mSocket.off("endRunning", onEndRunning) //
//        mSocket.off("opponentStopped", onOpponentStopped) //
//        mSocket.off("stopRunning", onStopRunning) //
//        mSocket.off("compareResult", onCompareResult) //
//        mSocket.off(Socket.EVENT_PING, onPing) //
//        mSocket.disconnect()
//    }

    companion object {
        const val JOB_ID = 1001
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, SocketService::class.java,
                JOB_ID, work)
        }
    }
}
