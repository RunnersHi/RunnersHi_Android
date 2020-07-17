package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.team.runnershi.util.PrefInit.Companion.prefs
import com.team.runnershi.data.AllRecordRecentContent
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.login.LoginActivity
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.onboard.OnBoardActivity
import kotlinx.android.synthetic.main.activity_rec_detail.*
import kotlinx.android.synthetic.main.activity_wait_me.*
import java.lang.Thread.sleep

@Suppress("DEPRECATION")
class WaitMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_me)

        val matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")
        matchData?.let{settingTextView(it)}

        Handler().postDelayed({
            val newIntent = Intent(this,CountDownActivity::class.java)
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

        tv_waitme_distData.text = (runWithmeData.distance/1000).toString()

        if (runWithmeData.pace_minute > 60)
            tv_waitme_pacedata.text = "-\'--\""
        else
            tv_waitme_pacedata.text =
                runWithmeData.pace_minute.toString() + "\'" + runWithmeData.pace_second.toString() + "\""

    }
}
