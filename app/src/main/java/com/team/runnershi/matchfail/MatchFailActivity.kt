package com.team.runnershi.matchfail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.team.runnershi.rundummy.MatchDummyActivity
import com.team.runnershi.R
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.data.RequestRecordRunWithme
import com.team.runnershi.data.RequestRecordRunWithmeAlone
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.extension.newStartActivity
import com.team.runnershi.home.HomeActivity
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.runalone.WaitMeActivity
import com.team.runnershi.util.PrefInit.Companion.prefs
import kotlinx.android.synthetic.main.activity_match_fail.*

class MatchFailActivity : AppCompatActivity() {
    val requestToServer = RequestToServer
    lateinit var matchData :RecordRunWithmeData
    var matchFailTime = -1
    var matchFailWantGender = -1
    var matchFailLevel = -1
    var isDummy = true
    var activityFlag = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_fail)
        activityFlag = 2 //intent.getIntExtra("activityFlag", -1) //1:matchProc, 2:GoalRunMe

        when (activityFlag) {
            1 -> {//1:matchProc
                getMatchProcIntent()
                requestMatchProcRunWithmeData()
            }

            2 -> {//2:GoalRunMe
                getGoalRunMeIntent()
            }
        }
    }

    private fun getMatchProcIntent() {
        matchFailTime = intent.getIntExtra("time", -1)
        matchFailWantGender = intent.getIntExtra("wantGender", -1)
        matchFailLevel = intent.getIntExtra("level", -1)
    }

    private fun getGoalRunMeIntent() {
        matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")!!
        isDummy = matchData.isDummy
        initUi()
    }

    private fun initUi(){
        //액티비티, 더미 여부에 따라 멘트 setting
        if(!isDummy){//나의 기록 존재, 기본 뷰 멘트 사용
        }else if(isDummy && activityFlag== 1) { // matchProc
            tv_match_fail_desc.text = "대신 다른러너의 기록과 러닝하시겠어요?\n나의 기록을 쌓아보세요."
        }else if(isDummy && activityFlag== 2){ //GoalRunMe
            tv_match_fail_title.text = "아쉽게도 해당하는\n기록을 찾을 수 없네요."
            tv_match_fail_desc.text = "대신 다른러너의 기록과 러닝하시겠어요?\n나의 기록을 쌓아보세요."
        }
        addBtnOnclickListener()
    }

    private fun addBtnOnclickListener(){
        btn_match_fail_bad.setOnClickListener {
            newStartActivity(HomeActivity::class.java)
            finish()
        }

        btn_match_fail_good.setOnClickListener {
            lateinit var newIntent : Intent
            if(isDummy)
                newIntent = Intent(this, MatchDummyActivity::class.java)
            else
                newIntent = Intent(this, WaitMeActivity::class.java)

            newIntent.putExtra("matchData",matchData)
            startActivity(newIntent)
            finish()
        }
    }

    private fun requestMatchProcRunWithmeData(){
        val token = prefs.getString("token", null)
        requestToServer.service.requestRecordRunWithme(
            token,
            RequestRecordRunWithme(
                level = matchFailLevel,
                gender = matchFailWantGender,
                time = matchFailTime
            )
        ).customEnqueue(
            onFailure = { call, t ->
                "requestRunWithme onFailure msg = ${t.message}".logDebug(MatchFailActivity::class.java)
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            matchData = body.result
                            isDummy = matchData.isDummy
                            initUi() //uiSetting
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }

}