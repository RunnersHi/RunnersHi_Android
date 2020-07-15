package com.team.runnershi

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.team.runnershi.extension.logDebug
import java.util.*

class RunSetViewModel : ViewModel() {
    val ldRunLeftTime = MutableLiveData<String>("00:00:00")
    val ldRunProgress = MutableLiveData<Int>()
    val ldTimeOver = MutableLiveData<Boolean>()
    var timer = Timer()
    private var runTime = -1
    private var prevProgress = -1
    private var leftTime = 0

    val ldCurrentLocation = MutableLiveData<Location>()
    val ldPath = MutableLiveData<MutableList<LatLng>>()
    val path = mutableListOf<LatLng>()

    var prevLocation: Location? = null
    var distStandard = 1
    val ldTotalMeter = MutableLiveData<Int>(0)
    private var totalDistance: Float = 0F
    private lateinit var prevLocation: Location

    fun showRunLeftTime(prevLeftTime: Int) {
        "Show Run Left Time (prevLeftTime: $prevLeftTime)".logDebug(
            this@RunSetViewModel
        )
        this.prevLeftTime = prevLeftTime
        "initial (prevLeftTime $prevLeftTime) (prevProgress: $prevProgress)".logDebug(this@RunSetViewModel)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                leftTime += 1
                val newLeftTime = this@RunSetViewModel.prevLeftTime - leftTime
                val hour = newLeftTime / (60 * 60)
                val minutes = newLeftTime % (60 * 60) / 60
                val seconds = newLeftTime % (60 * 60) % 60

                if (hour == 0) {
                    ldRunLeftTime.postValue("$minutes:$seconds")
                    "timer runLeftTime.postValue : ($minutes:$seconds)".logDebug(this@RunSetViewModel)
                } else {
                    ldRunLeftTime.postValue("$hour:$minutes:$seconds")
                    "timer runLeftTime.postValue : ($hour:$minutes:$seconds)".logDebug(this@RunSetViewModel)
                }


                "timer runProgress.postValue: $leftTime"
                ldRunProgress.postValue(leftTime)

            }
        }, ONE_SECOND, ONE_SECOND)
    }

    fun pathUpdate(currentLocation: Location) {
        path.add(LatLng(currentLocation))
        if (path.size >= 2) {
            ldPath.postValue(path)
        }
    }

    fun calTotalDistance(curLocation: Location) {
        if (prevLocation == null) {
            prevLocation = curLocation
        } else {
            val distance = prevLocation.distanceTo(curLocation)
            totalDistance += distance
            prevLocation = curLocation
//            ldTotalMeter.postValue()

//            ldTotalDistance.postValue(String.format("%.2f", distanceKm))
        }
    }

    fun checkTimerOver(passTime: Int) {
        ldTimeOver.value = (runTime <= passTime)
    }


    companion object {
        const val ONE_SECOND = 1000L
    }

}


