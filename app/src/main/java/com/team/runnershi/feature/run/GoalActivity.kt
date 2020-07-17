package com.team.runnershi.feature.run

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.team.runnershi.R
import kotlinx.android.synthetic.main.activity_goal.*

class GoalActivity : AppCompatActivity() {
    private var selectedRunTime: Int = 0
    private var clickable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        initData()
        initUi()
    }
    private fun initData(){
        goalActivity = this
        selectedRunTime = 30 //todo 테스트용임 바꾸어야함
    }


    private fun initUi() {
        radioGroup.setOnCheckedChangeListener { _, i ->
            clickable = true
            btn_goal_next.isClickable = true
            btn_goal_next.background =
                ContextCompat.getDrawable(this@GoalActivity, R.drawable.bg_btn_goal_next)
            btn_goal_next.setTextColor(ContextCompat.getColor(this@GoalActivity, R.color.white))
            selectedRunTime = when (i) {
                R.id.btn_goal_30 -> 30 //todo 30초로 바꾸어놓]
                R.id.btn_goal_45 -> 45 * 60
                R.id.btn_goal_60 -> 30 * 60 //todo 시연 대비로 막음
                R.id.btn_goal_90 -> 45 * 60 //todo 시연 대비로 막음
                else -> 30 * 60
            }
        }

        btn_goal_next.setOnClickListener {
            if(clickable){
                val intent = Intent(this, RivalActivity::class.java)
                intent.putExtra("runtime", selectedRunTime)
                startActivity(intent)
            }
        }

        btn_goal_back.setOnClickListener { finish() }

    }

    companion object{
        lateinit var goalActivity: GoalActivity
    }
}