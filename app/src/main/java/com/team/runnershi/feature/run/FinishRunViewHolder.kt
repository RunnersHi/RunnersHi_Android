package com.team.runnershi.feature.run

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.team.runnershi.extension.logDebug
import org.json.JSONArray
import org.json.JSONObject

class FinishRunViewHolder : ViewModel() {
    val lvIfComeResult = MutableLiveData(false)

    fun checkComeResult(gameIdx: Int, runIdx: Int) {
        with(gameIdx != -1 && runIdx != -1) {
            lvIfComeResult.postValue(this)
            if (!this) {
                "CheckComeResult() wrong (gameIdx: $gameIdx) (runIdx: $runIdx)".logDebug(this@FinishRunViewHolder)
            }
        }
    }
}