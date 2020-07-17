package com.team.runnershi.feature.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.R
import kotlinx.android.synthetic.main.activity_rival.*

class RivalActivity : AppCompatActivity() {
    private var selectedGender: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rival)

        initUi()
    }

    private fun initUi(){
        radioGroup.setOnCheckedChangeListener{_, i ->
            selectedGender = when(i){
                R.id.btn_rival_man ->  1
                R.id.btn_rival_woman ->  2
                R.id.btn_rival_random ->  3
                else -> return@setOnCheckedChangeListener
            }
        }

        btn_rival_back.setOnClickListener { finish() }

        btn_rival_next.setOnClickListener {
            val runtime = intent.getIntExtra("runtime", -1)
            val intent = Intent(this, MatchProcActivity::class.java)
            intent.putExtra("runtime", runtime)
            intent.putExtra("rungender", selectedGender)
            startActivity(intent)
        }
    }
}