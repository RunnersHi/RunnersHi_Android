package com.team.runnershi.recdetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.team.runnershi.R
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.activity_rec_detail.*

class RecDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    val requestToServer = RequestToServer
    private var naverMap: NaverMap? = null
    lateinit var path: PathOverlay
    val mapCoords = mutableListOf<LatLng>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec_detail)

        loadCoordinateDatas()
        settingMap()
        loadMyDatas()
        loadOpponentDatas()

    }

    fun settingMap() {
        //NaverMap 객체 얻어오기
        val fm = supportFragmentManager
        val mapvRecDetail = fm.findFragmentById(R.id.mapv_rec_detail) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapv_rec_detail, it).commit()
            }
        mapvRecDetail.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0

        //마커 세팅
        val marker = Marker()
        marker.position = mapCoords[mapCoords.size - 1]
        marker.icon = OverlayImage.fromResource(R.drawable.icon_location)
        marker.map = naverMap

        //지도에 경로 뿌리기
        if (mapCoords.size >= 2) {
            //경로객체 만들기
            path = PathOverlay()
            path.coords = mapCoords

            naverMap?.let {
                path.map = it

                //경로선 커스텀
                path.width = 20
                path.outlineWidth = 0
                path.color = Color.parseColor("#3868ff")

                //카메라 포커스 이동
                val coord = mapCoords[mapCoords.size - 1]
                it.moveCamera(CameraUpdate.scrollTo(coord))
            }
        }
    }

    fun loadCoordinateDatas() {
        var token = prefs.getString("token", null)
        requestToServer.service.requestRecordDetail(token, 69).customEnqueue(
            onFailure = { call, t ->
                Log.d(
                    "RecDetailActivity",
                    "requestRecDetail onFailure msg = ${t.message}"
                )
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            //DateData Setting
                            tvRecDetailDate.text =
                                body.result.month.toString() + "월 " + body.result.day.toString() + "일의 러닝"

                            //TimeData Setting
                            val sTimeArr = body.result.start_time!!.split(":")
                            val eTimeArr = body.result.end_time!!.split(":")

                            var sTimeTitle = ""
                            var eTimeTitle = ""

                            if (sTimeArr[0].toInt() < 12)
                                sTimeTitle = "오전"
                            else
                                sTimeTitle = "오후"

                            if (eTimeArr[0].toInt() < 12)
                                eTimeTitle = "오전"
                            else
                                eTimeTitle = "오후"

                            var sTimeHour = (sTimeArr[0].toInt() % 12)
                            var eTimeHour = (eTimeArr[0].toInt() % 12)

                            if (sTimeHour == 0)
                                sTimeHour = 12

                            if (eTimeHour == 0)
                                eTimeHour = 12

                            tvRecDetailTime.text =
                                sTimeTitle + sTimeHour.toString() + ":" + sTimeArr[1] + "-" + eTimeTitle + eTimeHour.toString() + ":" + eTimeArr[1]

                            //coordinate를 mapCoords: List<LatLng>에 Setting
                            val coordsDoubleArr = body.result.coordinate
                            for (coord in coordsDoubleArr) {
                                mapCoords.add(LatLng(coord.latitude, coord.longitude))
                            }

                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }

        )
    }

    fun loadMyDatas() {
        var token = prefs.getString("token", null)
        requestToServer.service.requestRecordRun(token, 69).customEnqueue(
            onFailure = { call, t ->
                Log.d(
                    "RecDetailActivity",
                    "requestRecordRun onFailure msg = ${t.message}"
                )
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            //dist
                            tvRecDetailDistData.text = body.result.distance.toString()
                            var paceArr = body.result.pace.toString()!!.split(".")
                            //pace
                            tvRecDetailPaceData.text = paceArr[0] + "\'" + paceArr[1] + "\""
                            //time
                            val timeArr = body.result.time!!.split(":")
                            if (timeArr[0].equals("00"))
                                tvRecDetailTimeData.text = timeArr[1] + ":" + timeArr[2]
                            else
                                tvRecDetailTimeData.text = body.result.time
                            //승패여부
                            if (body.result.result == 1)
                                cl_my_record.setBackgroundResource(R.drawable.bluebox_recdetailactivity)
                            else
                                cl_my_record.setBackgroundResource(R.drawable.graybox_recdetailactivity)
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }

    fun loadOpponentDatas() {
        var token = prefs.getString("token", null)
        requestToServer.service.requestRecordOpponent(token, 2).customEnqueue(
            onFailure = { call, t ->
                Log.d(
                    "RecDetailActivity",
                    "requestRecordOpponent onFailure msg = ${t.message}"
                )
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            //nickname
                            tvRecDetailRivalRecord.text = body.result.nickname+"의 기록"
                            //dist
                            tvRecDetailRivalDistData.text = body.result.distance.toString()
                            var paceArr = body.result.pace.toString()!!.split(".")
                            //pace
                            tvRecDetailRivalPaceData.text = paceArr[0] + "\'" + paceArr[1] + "\""
                            //time
                            val timeArr = body.result.time!!.split(":")
                            if (timeArr[0].equals("00"))
                                tvRecDetailRivalTimeData.text = timeArr[1] + ":" + timeArr[2]
                            else
                                tvRecDetailRivalTimeData.text = body.result.time
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }
}


