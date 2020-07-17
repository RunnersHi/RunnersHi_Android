package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_goal.*
import kotlinx.android.synthetic.main.activity_goal.radioGroup

class GoalActivity : AppCompatActivity() {
    private var selectedRunTime: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        selectedRunTime = 3*60 //todo 테스트용임 바꾸어야함
        initUi()
    }

    private fun initUi() {
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, i ->
            selectedRunTime = when (i) {
                R.id.btn_goal_30 -> 30 * 60
                R.id.btn_goal_45 -> 45 * 60
                R.id.btn_goal_60 -> 60 * 60 //todo 시연 대비로 막음
                R.id.btn_goal_90 -> 45 * 60 //todo 시연 대비로 막음
                else -> 30*60
            }
        })

        btn_goal_next.setOnClickListener {
            val intent = Intent(this, RivalActivity::class.java)
            intent.putExtra("runtime", selectedRunTime)
            startActivity(intent)
        }

        btn_goal_back.setOnClickListener { finish() }
    }
}