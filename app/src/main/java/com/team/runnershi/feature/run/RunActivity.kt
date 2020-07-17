package com.team.runnershi.feature.run

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.team.runnershi.R
import com.team.runnershi.socket.SocketService
import com.team.runnershi.extension.logDebug
import com.team.runnershi.feature.finishrun.FinishRunActivity
import kotlinx.android.synthetic.main.activity_run.*
import java.text.SimpleDateFormat
import java.util.*


class RunActivity : AppCompatActivity() {
    private var roomName = ""
    private var runtime = -1
    private var finalDist = -1
    private var finalCoords = arrayListOf<LatLng>()
    private var finalCreatedTime = ""
    private var finalEndTime = ""

    private val locationListener: LocationListener = RunLocationListener()

    //    private lateinit var currentLocation: Location
    private var naverMap: NaverMap? = null
    private val path = PathOverlay()
    private lateinit var locationOverLay: LocationOverlay
    private val pathCoords = mutableListOf<LatLng>()
    private lateinit var locationSource: GpsOnlyLocationSource
    private lateinit var runSetViewModel: RunSetViewModel

    private lateinit var socketReceiver: RunRecevier
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

        subscribe()
        checkPermission()
        initData()
        initUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(socketReceiver)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this@RunActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION_CODE
            )
        } else {
            bindLocationAndService()
        }
    }

    private fun bindLocationAndService() {
        BoundLocationManager.bindLocationListnerIn(
            this,
            locationListener,
            applicationContext
        )
    }

    @SuppressLint("MissingPermission")
    private fun initData() {
        finalCreatedTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
        locationSource =
            GpsOnlyLocationSource(this)

        val currentLocation =
            BoundLocationManager.boundLocationListener.mLocationManager?.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )
        currentLocation?.let {
            locationOverLay?.position = LatLng(it)
            runSetViewModel.ldCurrentLocation.postValue(it)
            runSetViewModel.pathUpdate(it)
            runSetViewModel.calTotalDistance(it)
            runSetViewModel.calPace(it)
            runSetViewModel.prevLocation = it
        }

        socketReceiver = RunRecevier()
        intentFilter = IntentFilter()
        intentFilter.addAction("com.team.runnershi.RESULT_END_RUNNING")
        registerReceiver(socketReceiver, intentFilter)
    }

    private fun subscribe() {
        runSetViewModel = ViewModelProvider(this).get(RunSetViewModel::class.java)
            .apply {
                ldRunLeftTime.observe(this@RunActivity, Observer { leftTimeText ->
                    tv_run_time_data.text = leftTimeText
                })
                ldRunProgress.observe(this@RunActivity, Observer { progress ->
                    progress_run.progress = progress
                    checkTimerOver(progress)
                })
                ldCurrentLocation.observe(this@RunActivity, Observer { location ->
                    naverMap?.let {
                        it.moveCamera(CameraUpdate.scrollTo(LatLng(location)))
                    }
                })
                ldTotalMeter.observe(this@RunActivity, Observer {
                    checkKmPassed(it)
                    if (ldIsKmPassed.value!!) {
                        sendKmPassed(distStandard)
                        incDistStandard()
                    }
                })
                ldTimeOver.observe(this@RunActivity, Observer {
                    if (it == true) {
                        timer.cancel()
                        finalDist = ldTotalMeter.value!!
//                        finalCoords = getFinalCoords()
                        finalCoords.addAll(path)
                        finalEndTime =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
                        sendEndRunning(roomName, ldTotalMeter.value!!)
                    }
                })
                ldTotalDistString.observe(this@RunActivity, Observer {
                    tv_run_dist_data.text = it
                })
                ldPace.observe(this@RunActivity, Observer {
                    tv_run_pace_data.text = it
                })

            }
        runSetViewModel.ldPath.observe(this, Observer { latLngList ->
            path.coords = latLngList
            path.map = naverMap
        })

    }

    private fun sendKmPassed(passedKm: Int) {
        val work = Intent()
        with(work) {
            putExtra("serviceFlag", "kmPassed")
            putExtra("roomName", roomName)
            putExtra("km", passedKm)
        }
        SocketService.enqueueWork(this, work)
    }

    private fun sendEndRunning(roomName: String, distance: Int) {
        val work = Intent()
            .apply {
                putExtra("serviceFlag", "endRunning")
                putExtra("roomName", roomName)
                putExtra("distance", distance)
            }
        SocketService.enqueueWork(this, work)
    }

    private fun initUi() {
        roomName = intent.getStringExtra("roomName")!!
        val profileImage: Int = getProfileImage()
        val name = intent.getStringExtra("name")
        val level = intent.getIntExtra("level", -1)
        val win = intent.getIntExtra("win", -1)
        val lose = intent.getIntExtra("lose", -1)
        runtime = intent.getIntExtra("runtime", -1)
        "ViewModel Update Parameter: $runtime".logDebug(this@RunActivity)
        val runtimeString = initProgressBarEndTV(runtime)
        progress_run.max = runtime

        Glide.with(this).load(profileImage).into(imgv_run_profile)
        tv_run_nickname.text = name
        tv_run_leve_data.text = getLevelData()
        tv_run_win_data.text = "${win}승 ${lose}패"
        tv_run_end.text = runtimeString
        runSetViewModel.showRunLeftTime(runtime)

        initMap()

        Glide.with(this).load(R.drawable.icon_unlock).into(btn_run_lock)
    }


    private fun getProfileImage(): Int {
        return when (intent.getIntExtra("image", -1)) {
            1 -> R.drawable.icon_redman_shorthair
            2 -> R.drawable.icon_blueman_shorthair
            3 -> R.drawable.icon_redman_basichair
            4 -> R.drawable.icon_blueman_permhair
            5 -> R.drawable.icon_redwomen_ponytail
            6 -> R.drawable.icon_bluewomen_ponytail
            7 -> R.drawable.icon_redman_shorthair
            8 -> R.drawable.icon_redwomen_shortmhair
            9 -> R.drawable.icon_redwomen_bunhair
            else -> {
                "GetProfileImage wrong number".logDebug(RunActivity::class)
                R.drawable.icon_redman_shorthair
            }
        }
    }

    private fun getLevelData(): String {
        return when (intent.getIntExtra("level", -1)) {
            1 -> "초급"
            2 -> "중급"
            3 -> "고급"
            else -> {
                "Get Level Data Wrong Level Data".logDebug(RunActivity::class)
                "초급"
            }
        }
    }

    private fun initProgressBarEndTV(runtime: Int): String = when (runtime) {
        30 * 60 -> "30:00"
        45 * 60 -> "45:00"
        60 * 60 -> "60:00"
        90 * 60 -> "1:30:00"
        2 * 60 -> "02:00"
        else -> "--:--"
    }

    private fun initMap() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_run_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_run_map, it).commit()
            }
        mapFragment.getMapAsync {
            this.naverMap = it
            locationOverLay = it.locationOverlay
            locationOverLay.isVisible = true
            locationOverLay.icon = OverlayImage.fromResource(R.drawable.icon_location)
        }

        path.width = 20
        path.outlineWidth = 0
        path.color = Color.parseColor("#3868ff")


    }


    private inner class RunLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            "OnLocationChanged".logDebug(this@RunActivity)
            runSetViewModel.ldCurrentLocation.postValue(location)
            runSetViewModel.calTotalDistance(location)
            runSetViewModel.calPace(location)
            naverMap?.let {
                it.moveCamera(CameraUpdate.scrollTo(LatLng(location)))
                runSetViewModel.pathUpdate(location)
                locationOverLay.apply {
                    this.position = LatLng(location)
                    this.bearing = location.bearing
                }
            }
            runSetViewModel.prevLocation = location
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String) {
            "Provider Enabled".logDebug(this@RunActivity)
        }

        override fun onProviderDisabled(provider: String) {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {

            bindLocationAndService()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {}

    companion object {
        val REQUEST_LOCATION_PERMISSION_CODE = 1000;
    }

    inner class RunRecevier() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.team.runnershi.RESULT_END_RUNNING" -> {
                    roomName = intent.getStringExtra("roomName")!!

                    Intent(
                        this@RunActivity,
                        FinishRunActivity::class.java
                    ).also { intent ->
                        intent.putExtra("roomName", roomName)
                        intent.putExtra("distance", finalDist)
                        intent.putExtra("runtime", runtime)
                        intent.putExtra("coordinates", finalCoords)
                        intent.putExtra("createdTime", finalCreatedTime)
                        intent.putExtra("endTime", finalEndTime)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}