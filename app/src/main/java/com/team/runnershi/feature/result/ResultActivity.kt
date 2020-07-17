package com.team.runnershi.feature.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.team.runnershi.R
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.feature.recdetail.RecDetailActivity
import com.team.runnershi.util.PrefInit.Companion.prefs
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    private val service = RequestToServer.service
    private val token = prefs.getString("token", null)
    private var gameIdx: Int = -1
    private var runIdx: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        requestResult()
        initButton()
    }


    private fun requestResult() {
        gameIdx = intent.getIntExtra("gameIdx", -1)
        runIdx = intent.getIntExtra("runIdx", -1)

        if(!intent.hasExtra("matchData")) {
            requestOpponenRecord()
        }
        else{
            val matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")
            tv_resultrival_rec.text = "${matchData!!.nickname}의 기록"
            tv_resultrival_distdata.text = convertMtoKm(matchData?.distance!!)
            tv_resultrival_pacedata.text =
                convertPace(matchData!!.pace_minute, matchData!!.pace_second)
            tv_resultrival_timedata.text = convertTime(matchData?.time!!)
        }
        requestMyRecentRecord()
    }

    private fun requestOpponenRecord() {
        service.requestRecordOpponent(
            token
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc0NiwiZXhwIjoxNTk0OTM0NzQ2fQ.LzMKe_o81YcxAN5akasZPXLfjM9S_t4lCIn기Zr8JdKFE" //6
////            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc2OSwiZXhwIjoxNTk0OTM0NzY5fQ.8eZnV47FRSjRj9eF_-L9x_CaiH1J2C82F2yU5NW01d0" // todo token 바꾸기
            , gameIdx
        )
            .customEnqueue(
                onFailure = { call, t ->
                    "Request Record Opponent Fail (errorMessage: ${t.message})".logDebug(this@ResultActivity)
                },
                onResponse = { call, response ->
                    if (response.isSuccessful) {
                        val body = response.body()
                        when (body?.status) {
                            200 -> {
                                if (body?.success) {
                                    val result = body.result
                                    tv_resultrival_rec.text = "${result!!.nickname}의 기록"
                                    tv_resultrival_distdata.text = convertMtoKm(result?.distance!!)
                                    tv_resultrival_pacedata.text =
                                        convertPace(result!!.paceMinute, result!!.paceSecond)
                                    tv_resultrival_timedata.text = convertTime(result?.time!!)
                                } else {
                                    "Request Recrod Opponent "
                                }

                            }
                            else -> {
                                "Request Record Opponent Response Status is not 200".logDebug(this@ResultActivity)
                            }
                        }
                    } else {
                        val errorCode = response.code()
                        val errorBody = response.errorBody().toString()
                        val errorMessage = response.message()
                        "Request Record Opponent is Not 200 (erroCode: $errorCode) (errorBody: $errorBody) (errorMessage: $errorMessage)".logDebug(
                            this@ResultActivity
                        )
                    }

                }
            )
    }

    private fun convertMtoKm(meter: Int): String {
        val km = meter.toFloat()?.div(1000)
        return String.format("%.2f", km)
    }

    private fun convertTime(time: String): String {
        val timeArr = time.split(":")
        return if (timeArr[0] == "00") {
            "${timeArr[1]}:${timeArr[2]}"
        } else {
            "0${(timeArr[0] as Int)}:${timeArr[1]}:${timeArr[2]}"
        }

    }

    private fun convertPace(minute: Int, second: Int): String {
        return when {
            minute >= 60 -> {
                "-'--\""
            }
            second < 10 -> {
                "$minute'0$second\""
            }
            else -> {
                "$minute'$second\""
            }
        }
    }

    private fun requestMyRecentRecord() {
        service.requestRecordRecent(
            token
//                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NyIsInBhc3N3b3JkIjoidGVzdHRlc3Q3IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc0NiwiZXhwIjoxNTk0OTM0NzQ2fQ.LzMKe_o81YcxAN5akasZPXLfjM9S_t4lCIn기Zr8JdKFE" //6
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3R0ZXN0NiIsInBhc3N3b3JkIjoidGVzdHRlc3Q2IiwidG9rZW4iOiJ0b2tlbiIsImlhdCI6MTU5NDg5ODc2OSwiZXhwIjoxNTk0OTM0NzY5fQ.8eZnV47FRSjRj9eF_-L9x_CaiH1J2C82F2yU5NW01d0"
        ).customEnqueue(onFailure = { call, t ->
            "Request My Record Fail (errorMessage: ${t.message})".logDebug(this@ResultActivity)
        },
            onResponse = { call, response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    when (body?.status) {
                        200 -> {
                            if (body?.success) {
                                val result = body?.result
                                tv_resultdetail_distdata.text = convertMtoKm(result?.distance!!)
                                tv_resultdetail_pacedata.text =
                                    convertPace(result?.paceMinute!!, result?.paceSecond!!)
                                tv_resultdetail_timedata.text = convertTime(result?.time!!)
                                when (result?.result) {
                                    1 -> {
                                        tv_result_title.text = "WINNER"
                                        tv_result_title.setTextColor(
                                            ContextCompat.getColor(
                                                this@ResultActivity,
                                                R.color.lightish_blue
                                            )
                                        )
                                    }
                                    else -> {
                                        tv_result_title.text = "TRY AGAIN"

                                    }
                                }
                                when (result?.image) {
                                    1 -> Glide.with(this).load(R.drawable.icon_redman_shorthair)
                                        .into(imgv_result_profile)
                                    2 -> Glide.with(this).load(R.drawable.icon_blueman_shorthair)
                                        .into(imgv_result_profile)
                                    3 -> Glide.with(this).load(R.drawable.icon_redman_basichair)
                                        .into(imgv_result_profile)
                                    4 -> Glide.with(this).load(R.drawable.icon_blueman_permhair)
                                        .into(imgv_result_profile)
                                    5 -> Glide.with(this).load(R.drawable.icon_redwomen_ponytail)
                                        .into(imgv_result_profile)
                                    6 -> Glide.with(this).load(R.drawable.icon_bluewomen_ponytail)
                                        .into(imgv_result_profile)
                                    7 -> Glide.with(this).load(R.drawable.icon_redman_shorthair)
                                        .into(imgv_result_profile)
                                    8 -> Glide.with(this).load(R.drawable.icon_redwomen_shortmhair)
                                        .into(imgv_result_profile)
                                    9 -> Glide.with(this).load(R.drawable.icon_redwomen_bunhair)
                                        .into(imgv_result_profile)
                                    else -> Glide.with(this).load(R.drawable.icon_redwomen_bunhair)
                                        .into(imgv_result_profile)
                                }
                            } else {
                                "Request MyRecent Record Code is 200 but No Exist Record (message: ${body?.message})".logDebug(
                                    this@ResultActivity
                                )
                            }
                        }
                        else -> {
                            "Request Record Opponent Response Status is not 200".logDebug(this@ResultActivity)
                        }
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody().toString()
                    val errorMessage = response.message()
                    "Request Record Opponent is Not 200 (erroCode: $errorCode) (errorBody: $errorBody) (errorMessage: $errorMessage)".logDebug(
                        this@ResultActivity
                    )
                }
            })
    }

    private fun initButton(){
        btn_detail_rec.setOnClickListener {
            "From ResultActivity to RecDetail (runIdx :$runIdx) (gameIdx: $gameIdx)".logDebug(this@ResultActivity)
            Intent(this@ResultActivity, RecDetailActivity::class.java)
                .also {intent ->
                    intent.putExtra("runIdx", runIdx)
                    intent.putExtra("gameIdx", gameIdx)
                    startActivity(intent)
                }
        }
    }


}