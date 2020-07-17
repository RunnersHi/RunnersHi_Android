package com.team.runnershi.feature.runalone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
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
import com.team.runnershi.*
import com.team.runnershi.data.CoordinateData
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.data.RequestRecordRunPost
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.feature.run.GpsOnlyLocationSource
import com.team.runnershi.feature.run.RunActivity
import com.team.runnershi.util.PrefInit.Companion.prefs
import kotlinx.android.synthetic.main.activity_run_me.*
import java.text.SimpleDateFormat
import java.util.*


class RunMeActivity : AppCompatActivity() {
    val requestToServer = RequestToServer
    private lateinit var matchData: RecordRunWithmeData
    private var runtime = -1
    private var finalDist = -1
    private var finalCoords = mutableListOf<CoordinateData>()
    private var finalCreatedTime = ""
    private var finalEndTime = ""
    private val locationListener: LocationListener = RunMeLocationListener()
    private var naverMap: NaverMap? = null
    private val path = PathOverlay()
    private lateinit var locationOverLay: LocationOverlay
    private val pathCoords = mutableListOf<LatLng>()
    private lateinit var locationSource: GpsOnlyLocationSource
    private lateinit var runMeSetViewModel: RunMeSetViewModel
    lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_me)
        subscribe()
        checkPermission()
        initData()
        initUi()
        initTTS()
        setSpeakTimer()
    }

    override fun onDestroy() {
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }

    private fun bindLocationAndService() {
        RunMeBoundLocationManager.bindLocationListnerIn(
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
            RunMeBoundLocationManager.runMeBoundLocationListener.mLocationManager?.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )
        currentLocation?.let {
            locationOverLay?.position = LatLng(it)
            runMeSetViewModel.ldCurrentLocation.postValue(it)
            runMeSetViewModel.pathUpdate(it)
            runMeSetViewModel.calTotalDistance(it)
            runMeSetViewModel.calPace(it)
            runMeSetViewModel.prevLocation = it
        }
    }

    private fun subscribe() {
        runMeSetViewModel = ViewModelProvider(this).get(RunMeSetViewModel::class.java)
            .apply {
                ldRunLeftTime.observe(this@RunMeActivity, Observer { leftTimeText ->
                    tv_run_me_time_data.text = leftTimeText
                })
                ldRunProgress.observe(this@RunMeActivity, Observer { progress ->
                    progress_run_me.progress = progress
                    checkTimerOver(progress)
                })
                ldCurrentLocation.observe(this@RunMeActivity, Observer { location ->
                    naverMap?.let {
                        it.moveCamera(CameraUpdate.scrollTo(LatLng(location)))
                    }
                })
                ldTotalMeter.observe(this@RunMeActivity, Observer {
                    checkKmPassed(it)
                    if (ldIsKmPassed.value!!) {
                        incDistStandard()
                    }
                })
                ldTimeOver.observe(this@RunMeActivity, Observer {
                    if (it == true) {
                        timer.cancel()
                        finalDist = ldTotalMeter.value!!
                        for (items in path) {
                            finalCoords.add(CoordinateData(items.latitude, items.longitude))
                        }
                        finalEndTime =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)

                        sendToServer()
                    }
                })
                ldTotalDistString.observe(this@RunMeActivity, Observer {
                    tv_run_me_dist_data.text = it
                })
                ldPace.observe(this@RunMeActivity, Observer {
                    tv_run_me_pace_data.text = it
                })
            }
        runMeSetViewModel.ldPath.observe(this, Observer { latLngList ->
            path.coords = latLngList
            path.map = naverMap
        })
    }

    private fun initUi() {
        matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")!!
        val profileImage: Int = getProfileImage()
        val name = matchData.nickname
        val level = matchData.level
        val win = matchData.win
        val lose = matchData.lose
        runtime = timeToInteger(matchData.time) //**test**//
        "ViewModel Update Parameter: $runtime".logDebug(this@RunMeActivity)
        val runtimeString = initProgressBarEndTV(runtime)
        progress_run_me.max = runtime
        Glide.with(this).load(profileImage).into(imgv_run_me_profile)
        tv_run_me_nickname.text = name
        tv_run_me_leve_data.text = getLevelData()
        tv_run_me_win_data.text = "${win}승 ${lose}패"
        tv_run_me_end.text = runtimeString
        runMeSetViewModel.showRunLeftTime(runtime)
        initMap()
        Glide.with(this).load(R.drawable.icon_unlock).into(btn_run_me_lock)
    }

    private fun getProfileImage(): Int {
        return when (matchData.image) {
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

    private fun timeToInteger(time: String): Int = when (time) {
        "00:30:00" -> 30 * 60
        "00:45:00" -> 45 * 60
        "01:00:00" -> 60 * 60
        "01:30:00" -> 90 * 60
        "02:00" -> 2 * 60
        else -> 30 * 60
    }

    private fun initMap() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_run_me_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_run_me_map, it).commit()
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

    private inner class RunMeLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            "OnLocationChanged".logDebug(this@RunMeActivity)
            runMeSetViewModel.ldCurrentLocation.postValue(location)
            runMeSetViewModel.calTotalDistance(location)
            runMeSetViewModel.calPace(location)
            naverMap?.let {
                it.moveCamera(CameraUpdate.scrollTo(LatLng(location)))
                runMeSetViewModel.pathUpdate(location)
                locationOverLay.apply {
                    this.position = LatLng(location)
                    this.bearing = location.bearing
                }
            }
            runMeSetViewModel.prevLocation = location
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String) {
            "Provider Enabled".logDebug(this@RunMeActivity)
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

    companion object {
        val REQUEST_LOCATION_PERMISSION_CODE = 1000;
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this@RunMeActivity,
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

    private fun sendToServer() {
        val token = prefs.getString("token", null)
        var result = -1
        if (matchData.distance < finalDist)
            result = 1 //승
        else
            result = 2 //패
        requestToServer.service.requestRecordRunPost(
            token,
            RequestRecordRunPost(
                distance = finalDist + 1,
                time = runtime,
                result = result,
                created_time = finalCreatedTime,
                end_time = finalEndTime,
                coordinates = finalCoords
            )
        ).customEnqueue(
            onFailure = { call, t ->
                "requestRunPost onFailure msg = ${t.message}".logDebug(this)
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            val runIdx = body.result.run_idx
                            val gameIdx = body.result.game_idx
                            "requestRecordRunPost success, run_idx = (${runIdx}), game_idx = (${gameIdx}) "

                            val intent = Intent(this, FinishRunMeActivity::class.java)
                            intent.putExtra("runIdx", runIdx)
                            intent.putExtra("gameIdx", gameIdx)
                            intent.putExtra("activityFlag", 2)
                            intent.putExtra("matchData",matchData)
                            startActivity(intent)
                            finish()
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

    fun initTTS() {
        //tts
        tts = TextToSpeech(this, TextToSpeech.OnInitListener { i ->
            if (i == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.KOREA)
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })

    }

    //tts
    fun speakKmPassed(km: Int) {
        tts.speak("상대가 " + km.toString() + "키로미터를 돌파했습니다", TextToSpeech.QUEUE_FLUSH, null)
    }

    fun setSpeakTimer() {
        var km = 0
        val opponentPaceTime = (matchData.pace_minute * 60 * 1000).toLong()
        val timer = object : CountDownTimer(runtime * 1000L, opponentPaceTime) {
            override fun onTick(millisUntilFinished: Long) {
                speakKmPassed(km++)
                "onTick(), countDownInterval= (${(matchData.pace_minute * 60 * 1000 + matchData.pace_second * 1000).toLong()}), call speakKmPassed(${km}) "
            }

            override fun onFinish() {
                "onFinish()".logDebug(this)
            }
        }
        timer.start()
    }

}
