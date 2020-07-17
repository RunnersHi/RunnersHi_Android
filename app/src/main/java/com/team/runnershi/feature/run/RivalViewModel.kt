package com.team.runnershi.feature.run

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RivalViewModel : ViewModel() {
    val isClickable = MutableLiveData<Boolean>(false)

    fun checkClickable(selectedGender: Int) {
        isClickable.postValue(
            (selectedGender == 1
                    || selectedGender == 2
                    || selectedGender == 3)
        )
    }
}