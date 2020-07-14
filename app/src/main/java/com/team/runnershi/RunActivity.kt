package com.team.runnershi

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import com.naver.maps.map.overlay.PathOverlay
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_run.*


class RunActivity : AppCompatActivity() {
    private var roomName = ""
    private val locationListener: LocationListener = RunLocationListener()
    private var naverMap: NaverMap? = null

    private lateinit var locationSource: GpsOnlyLocationSource
    private val path = PathOverlay()
    private val pathCoords = mutableListOf<LatLng>()
    private lateinit var runSetViewModel: RunSetViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)
        checkPermission()
        initData()
        subscribe()
        initUi()
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
            bindLocationListener()
        }
    }

    private fun bindLocationListener() {
        BoundLocationManager.bindLocationListnerIn(this, locationListener, applicationContext)
    }

    private fun initData() {
        locationSource = GpsOnlyLocationSource(this)
        runSetViewModel = ViewModelProvider(this).get(RunSetViewModel::class.java)

    }

    private fun subscribe() {
        val leftTimeObserver = Observer<String>() { leftTimeText ->
            tv_run_time_data.text = leftTimeText
        }
        runSetViewModel.ldRunLeftTime.observe(this, leftTimeObserver)

        val progressObserver = Observer<Int> { progress ->
            progress_run.progress = progress
        }
        runSetViewModel.ldRunProgress.observe(this, progressObserver)

        runSetViewModel.runLeftTime.observe(this, leftTimeObserver)
    }

    private fun initUi() {
        roomName = intent.getStringExtra("roomName")!!
        val profileImage = getProfileImage()
        val name = intent.getStringExtra("name")
        val level = intent.getIntExtra("level", -1)
        val win = intent.getIntExtra("win", -1)
        val lose = intent.getIntExtra("lose", -1)
        val runtime = intent.getIntExtra("runtime", -1)
        "ViewModel Update Parameter: $runtime".logDebug(this@RunActivity)
        val runtimeString = initProgressBarEndTV(runtime)
        progress_run.max = runtime

        Glide.with(this).load(profileImage).into(imgv_run_profile)
        tv_run_nickname.text = name
        tv_run_leve_data.text = getLevelData()
        tv_run_win_data.text = "${win}승 ${lose}패"
        tv_run_end.text = runtimeString
        runSetViewModel.showRunLeftTime(runtime)


        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_run_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_run_map, it).commit()
            }

        mapFragment.getMapAsync {
            this.naverMap = it
        }

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
            1 -> "초"
            2 -> "중"
            3 -> "고"
            else -> {
                "Get Level Data Wrong Level Data".logDebug(RunActivity::class)
                "초"
            }
        }
    }

    private fun initProgressBarEndTV(runtime: Int): String = when (runtime) {
        30 * 60 -> "30:00"
        45 * 60 -> "45:00"
        60 * 60 -> "60:00"
        90 * 60 -> "1:30:00"
        else -> "--:--"
    }

    private inner class RunLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            naverMap?.let {
                val coord = LatLng(location)
                pathCoords.add(coord)
                if (pathCoords.size >= 2) {
                    path.coords = pathCoords
                    path.map = it
                }

                val locationOverlay = it.locationOverlay
                locationOverlay.apply {
                    this.isVisible = false
                    this.position = coord
                    this.bearing = location.bearing
                }

                it.moveCamera(CameraUpdate.scrollTo(coord))
            }


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

            bindLocationListener()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val REQUEST_LOCATION_PERMISSION_CODE = 1000;
    }


}