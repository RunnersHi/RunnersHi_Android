package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.extension.newStartActivity
import kotlinx.android.synthetic.main.activity_match_fail.*

class MatchFailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_fail)

        val matchFailExistRecord = intent.getIntExtra("existRecord",1)
        val matchFailTime = intent.getIntExtra("time",30)
        val matchFailwantGender = intent.getIntExtra("wantGender",1)
        val matchFailLevel = intent.getIntExtra("level",2)

        if(matchFailExistRecord==0) //이전기록 없음
            tv_match_fail_desc.text = "대신 다른 러너의 기록과 러닝하시겠어요?\n첫 러닝을 지금 시작해보세요"
        else
            tv_match_fail_desc.text = "대신 나의 기록과 러닝하시겠어요?\n어제의 나를 이겨보세요"


        btn_match_fail_bad.setOnClickListener {
            newStartActivity(HomeActivity::class.java)
            finish()
        }

        btn_match_fail_good.setOnClickListener {
            lateinit var newIntent :Intent
            if(matchFailExistRecord==0)
                newIntent = Intent(this, MatchDummyActivity::class.java)
            else
                newIntent = Intent(this, WaitMeActivity::class.java)

            newIntent.putExtra("existRecord",matchFailExistRecord)
            newIntent.putExtra("time",matchFailTime)
            newIntent.putExtra("wantGender",matchFailwantGender)
            newIntent.putExtra("level",matchFailLevel)

            startActivity(newIntent)
            finish()
        }
    }
}