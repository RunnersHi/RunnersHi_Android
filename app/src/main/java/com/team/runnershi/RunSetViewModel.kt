package com.team.runnershi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunSetViewModel : ViewModel() {
    var runTime: MutableLiveData<Int> = MutableLiveData()

    fun changeRunTimeToHour(runTime: MutableLiveData<Int>): String
        = when(runTime.value){
            30 -> "00:30"
            45 -> "00:45"
            60 -> "01:00"
            90 -> "01:30"
            else -> "--:--"
        }



}


