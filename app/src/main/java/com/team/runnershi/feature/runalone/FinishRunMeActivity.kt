package com.team.runnershi.feature.runalone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.R
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.feature.result.ResultActivity
import kotlinx.android.synthetic.main.activity_finish_run_me.*

class FinishRunMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_run_me)

        val newIntent = Intent(this,ResultActivity::class.java)
        val runIdx = intent.getIntExtra("runIdx", -1)
        val gameIdx = intent.getIntExtra("gameIdx", -1)
        val matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")

        newIntent.putExtra("runIdx", runIdx)
        newIntent.putExtra("gameIdx", gameIdx)
        newIntent.putExtra("matchData",matchData)

        btnFinishRunMeConfirm.setOnClickListener {
            startActivity(newIntent)
            finish()
        }

    }
}