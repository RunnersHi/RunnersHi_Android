package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.result.ResultActivity
import kotlinx.android.synthetic.main.activity_finish_run.*

class FinishRunMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_run_me)

        val newIntent = Intent(this,ResultActivity::class.java)
        val runIdx = intent.getIntExtra("runIdx", -1)
        val gameIdx = intent.getIntExtra("gameIdx", -1)

        newIntent.putExtra("runIdx", runIdx)
        newIntent.putExtra("gameIdx", gameIdx)

        btnFinishRunConfirm.setOnClickListener {
            startActivity(newIntent)
            finish()
        }

    }
}