package com.team.runnershi.rundummy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock.sleep
import android.util.Log
import com.team.runnershi.R
import com.team.runnershi.countdown.CountDownActivity
import com.team.runnershi.data.RequestDummyData
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import kotlinx.android.synthetic.main.activity_match_dummy.*

class MatchDummyActivity : AppCompatActivity() {

    val imgv_dummy_face1 = R.drawable.icon_redman_shorthair
    val imgv_dummy_face2 = R.drawable.icon_blueman_shorthair
    val imgv_dummy_face3 = R.drawable.icon_redman_basichair
    val imgv_dummy_face4 = R.drawable.icon_blueman_permhair
    val imgv_dummy_face5 = R.drawable.icon_redwomen_ponytail
    val imgv_dummy_face6 = R.drawable.icon_bluewomen_ponytail
    val imgv_dummy_face7 = R.drawable.icon_redwomen_shortmhair
    val imgv_dummy_face8 = R.drawable.icon_bluewomen_permhair
    val imgv_dummy_face9 = R.drawable.icon_redwomen_bunhair

    val dummy_face = arrayOf(
        imgv_dummy_face1,
        imgv_dummy_face2,
        imgv_dummy_face3,
        imgv_dummy_face4,
        imgv_dummy_face5,
        imgv_dummy_face6,
        imgv_dummy_face7,
        imgv_dummy_face8,
        imgv_dummy_face9
    )

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_dummy)

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
        tv_match_dummy_nick_name.text = runWithmeData.nickname
        tv_match_dummy_lv_data.text = runWithmeData.level.toString()
        tv_match_dummy_lv_score_data.text = "${runWithmeData.win}승 ${runWithmeData.lose}패"
        imageView_dummy.setImageResource(dummy_face[runWithmeData.image])
    }

}