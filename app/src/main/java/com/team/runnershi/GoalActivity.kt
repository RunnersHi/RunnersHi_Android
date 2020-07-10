package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.team.runnershi.extension.newStartActivity
import kotlinx.android.synthetic.main.activity_goal.*

class GoalActivity : AppCompatActivity() {
    private lateinit var runSetViewModel: RunSetViewModel
    private var runTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)


        runSetViewModel =
            ViewModelProvider(this).get(com.team.runnershi.RunSetViewModel::class.java)
        initUi()
    }

    private fun initUi() {
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, i ->
            runTime = when (i) {
                R.id.btn_goal_30 -> 30
                R.id.btn_goal_45 -> 45
                R.id.btn_goal_60 -> 60
                R.id.btn_goal_90 -> 90
                else -> return@OnCheckedChangeListener
            }
        })

        btn_goal_next.setOnClickListener {
            runSetViewModel.runTime.postValue(runTime)
            this.newStartActivity(RivalActivity::class.java)
        }


    }
}