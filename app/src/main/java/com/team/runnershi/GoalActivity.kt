package com.team.runnershi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_goal.*

class GoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        initUi()
    }

    private fun initUi(){
        Glide.with(this).load(R.drawable.btn_back).into(btn_goal_back)
        btn_goal_30.setOnClickListener(btnGoalListener)
        btn_goal_45.setOnClickListener(btnGoalListener)
        btn_goal_60.setOnClickListener(btnGoalListener)
        btn_goal_90.setOnClickListener(btnGoalListener)


    }

    private val btnGoalListener: View.OnClickListener =
        View.OnClickListener {
            when(it.id){
                R.id.btn_goal_30 ->{
                    btn_goal_30.background = this.getDrawable(R.drawable.bg_btn_goal_clicked)
                    btn_goal_45.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_60.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_90.background = this.getDrawable(R.drawable.bg_btn_goal)
                }
                R.id.btn_goal_45 ->{
                    btn_goal_30.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_45.background = this.getDrawable(R.drawable.bg_btn_goal_clicked)
                    btn_goal_60.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_90.background = this.getDrawable(R.drawable.bg_btn_goal)
                }
                R.id.btn_goal_60 ->{
                    btn_goal_30.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_45.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_60.background = this.getDrawable(R.drawable.bg_btn_goal_clicked)
                    btn_goal_90.background = this.getDrawable(R.drawable.bg_btn_goal)
                }
                R.id.btn_goal_90 ->{
                    btn_goal_30.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_45.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_60.background = this.getDrawable(R.drawable.bg_btn_goal)
                    btn_goal_90.background = this.getDrawable(R.drawable.bg_btn_goal_clicked)
                }

            }
        }


}