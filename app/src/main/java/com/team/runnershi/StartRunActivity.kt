package com.team.runnershi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StartRunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_run)

        goRunActivity()
    }
    private fun goRunActivity(){
        val intent = Intent(this@StartRunActivity, RunActivity::class.java)
        with(intent) {
            putExtra(
                "roomName",
                intent.getStringExtra("roomName")
            )
            putExtra(
                "name",
                intent.getStringExtra("name")
            )
            putExtra(
                "level",
                intent.getIntExtra("level", -1)
            )
            putExtra(
                "image",
                intent.getIntExtra("image", -1)
            )
            putExtra(
                "win",
                intent.getIntExtra("win", -1)
            )
            putExtra(
                "lose",
                intent.getIntExtra("lose", -1)
            )
            putExtra(
                "runtime",
                intent.getIntExtra("runtime", -1)
            )
        }
        startActivity(intent)
    }
}