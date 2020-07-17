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
    val ldTotalDistString = MutableLiveData<String>("0.00")
    val ldPace = MutableLiveData<String>("00\'00\"")
    val ldIsKmPassed = MutableLiveData<Boolean>(false)

    fun calTotalDistance(curLocation: Location) {
        if (prevLocation == null) {
            prevLocation = curLocation
        } else {
            val interval = prevLocation!!.distanceTo(curLocation).toInt()
            ldTotalMeter.value?.let { curTotalMeter ->
                ldTotalMeter.postValue(curTotalMeter + interval)
            }

            val totalKm = ldTotalMeter.value?.toFloat()?.div(1000)
            ldTotalDistString.postValue(String.format("%.2f", totalKm))
        }
    }

    fun checkKmPassed(totalMeter: Int) {
//        ldIsKmPassed.value = (totalMeter / 1000 >= distStandard)
        ldIsKmPassed.value = (totalMeter/5 >= distStandard)
    }

    fun incDistStandard() {
        distStandard += 1
    }

    fun calPace(curLocation: Location) {
        if (prevLocation == null) {
            prevLocation = curLocation
        } else {
            val timeIntervalSec = (curLocation.time - prevLocation!!.time).toFloat() / 1000
            val distIntervalMeter = prevLocation!!.distanceTo(curLocation)
            val paceMinute =
                if (distIntervalMeter == 0f) {
                    0f
                } else {
                    (timeIntervalSec / 60f) / (distIntervalMeter / 1000f)
                }
            val paceSeconds =
                if (distIntervalMeter == 0f) 0f
                else {
                    (paceMinute - paceMinute.toInt().toFloat()) * 60f
                }

            if (paceMinute >= 60) {
                ldPace.postValue("-'--\"")
                "Pace PostValue: -'--\"".logDebug(this@RunSetViewModel)
            } else {
                ldPace.postValue("${paceMinute.toInt()}'${paceSeconds.toInt()}\"")
                "Pace PostValue (paceMinute.toInt(): ${paceMinute.toInt()} paceSeconds: ${paceSeconds.toInt()})".logDebug(
                    this@RunSetViewModel
                )
            }
        }
    }


    fun showRunLeftTime(prevLeftTime: Int) {
        "Show Run Left Time (prevLeftTime: $prevLeftTime)".logDebug(
            this@RunSetViewModel
        )
        this.runTime = prevLeftTime
        "initial (prevLeftTime $prevLeftTime) (prevProgress: $prevProgress)".logDebug(this@RunSetViewModel)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                leftTime += 1
                val newLeftTime = this@RunSetViewModel.runTime - leftTime
                val hour = newLeftTime / (60 * 60)
                val minutes = newLeftTime % (60 * 60) / 60
                val seconds = newLeftTime % (60 * 60) % 60

                val strHour = if (hour == 0) "" else "$hour:"
                val strMinutes = if (minutes >= 10) "$minutes:" else "0$minutes:"
                val strSeconds = "$seconds"
                ldRunLeftTime.postValue("$strHour$strMinutes$strSeconds")
                "timer runLeftTime.postValue (hour: $hour) (minutes: $minutes) (seconds: $seconds)".logDebug(
                    this@RunSetViewModel
                )

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


    fun getFinalCoords(): ArrayList<List<Double>> {
        val finalPath = path
        val finalCoords = arrayListOf<List<Double>>()
        for (point in finalPath) {
            finalCoords.add(listOf(point.latitude, point.longitude))
        }
        return finalCoords
    }

    fun checkTimerOver(passTime: Int) {
        ldTimeOver.value = (runTime <= passTime)
    }


    companion object {
        const val ONE_SECOND = 1000L
    }

}


