package com.team.runnershi.feature.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.team.runnershi.R
import com.team.runnershi.feature.run.GoalActivity.Companion.goalActivity
import kotlinx.android.synthetic.main.activity_rival.*
import kotlinx.android.synthetic.main.activity_rival.radioGroup

class RivalActivity : AppCompatActivity() {
    private var selectedGender: Int = 0
    private lateinit var rivalViewModel: RivalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rival)

        initUi()
    }

    private fun initUi() {
        rivalViewModel =
            ViewModelProvider(this@RivalActivity).get(com.team.runnershi.feature.run.RivalViewModel::class.java)
        rivalViewModel.apply {
            isClickable.observe(this@RivalActivity, Observer {
                btn_rival_next.isClickable = it
            })
        }

        radioGroup.setOnCheckedChangeListener { _, i ->
            btn_rival_next.background =
                ContextCompat.getDrawable(this@RivalActivity, R.drawable.bg_btn_goal_next)
            btn_rival_next.setTextColor(ContextCompat.getColor(this@RivalActivity, R.color.white))
            selectedGender = when (i) {
                R.id.btn_rival_man -> 1
                R.id.btn_rival_woman -> 2
                R.id.btn_rival_random -> 3
                else -> return@setOnCheckedChangeListener
            }
            rivalViewModel.checkClickable(selectedGender)
        }


        btn_rival_back.setOnClickListener { finish() }

        btn_rival_next.setOnClickListener {
            val runtime = intent.getIntExtra("runtime", -1)
            val intent = Intent(this, MatchProcActivity::class.java)
            intent.putExtra("runtime", runtime)
            intent.putExtra("rungender", selectedGender)
            startActivity(intent)
            goalActivity.finish()
            finish()
        }
    }

}