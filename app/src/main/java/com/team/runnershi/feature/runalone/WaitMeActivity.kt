package com.team.runnershi.feature.runalone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.team.runnershi.R
import com.team.runnershi.feature.runcountdown.CountDownActivity
import com.team.runnershi.data.RecordRunWithmeData
import kotlinx.android.synthetic.main.activity_wait_me.*

@Suppress("DEPRECATION")
class WaitMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_me)

        val matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")
        matchData?.let{settingTextView(it)}

        Handler().postDelayed({
            val newIntent = Intent(this, CountDownActivity::class.java)
            newIntent.putExtra("matchData",matchData)
            startActivity(newIntent)
            finish()
        }, 3000)
    }

    fun settingTextView(runWithmeData: RecordRunWithmeData ) {

        var resultTime = runWithmeData.time
        var splitTimeItem = resultTime.split(":")

        if (splitTimeItem[0] == "00") {
            tv_waitme_pacetimedata.text = "${splitTimeItem[1]}:${splitTimeItem[2]}"
        } else {
            tv_waitme_pacetimedata.text = "${splitTimeItem[0][1]}:${splitTimeItem[1]}:${splitTimeItem[2]}"
        }

        var dist_change = runWithmeData.distance/1000.0
        val change_dist = String.format("%.2f", dist_change).toDouble()
        tv_waitme_distData.text = change_dist.toString()

        if (runWithmeData.pace_minute >= 60)
            tv_waitme_pacedata.text = "-\'--\""
        else
            tv_waitme_pacedata.text =
                runWithmeData.pace_minute.toString() + "\'" + runWithmeData.pace_second.toString() + "\""

    }
}
