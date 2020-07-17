package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.data.RequestRecordRunWithmeAlone
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.matchfail.MatchFailActivity
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.util.PrefInit
import kotlinx.android.synthetic.main.activity_goal_run_me.*
import kotlinx.android.synthetic.main.activity_goal.radioGroup

class GoalRunMeActivity : AppCompatActivity() {
    val requestToServer = RequestToServer
    private var selectedRunTime: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_run_me)
        initUi()
    }

    private fun initUi() {
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, i ->
            selectedRunTime = when (i) {
                R.id.btn_goal_run_me_30 -> 30 * 60
                R.id.btn_goal_run_me_45 -> 45 * 60
                R.id.btn_goal_run_me_60 -> 60 * 60
                R.id.btn_goal_run_me_90 -> 90 * 60
                else -> return@OnCheckedChangeListener
            }
        })

        btn_goal_run_me_next.setOnClickListener {
            requestGoalRunMeRunWithmeData()
        }

        btn_goal_run_me_back.setOnClickListener { finish() }
    }

    private fun requestGoalRunMeRunWithmeData(){
        val token = PrefInit.prefs.getString("token", null)
        requestToServer.service.requestRecordRunWithmeAlone(
            token,
            RequestRecordRunWithmeAlone(
                time = selectedRunTime
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
                            lateinit var newIntent :Intent
                            if(body.result.isDummy)
                                newIntent = Intent(this, MatchFailActivity::class.java)
                            else
                                newIntent = Intent(this, WaitMeActivity::class.java)
                            newIntent.putExtra("matchData", body.result)
                            newIntent.putExtra("activityFlag", 2)
                            startActivity(newIntent)
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