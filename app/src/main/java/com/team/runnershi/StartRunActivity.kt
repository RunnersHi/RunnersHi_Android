package com.team.runnershi

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.team.runnershi.data.RecordRunWithmeData
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_start_run.*

class StartRunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_run)

        initUi()
    }

    private fun initUi() {
        ani_run.playAnimation()
        ani_run.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                if (intent.hasExtra("roomName"))
                    runWithOpponent()
                else
                    runWithMe()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}

        })
    }

    fun runWithOpponent() {
        val roomName = intent.getStringExtra("roomName")
        val name = intent.getStringExtra("name")
        val level = intent.getIntExtra("level", -1)
        val image = intent.getIntExtra("image", -1)
        val win = intent.getIntExtra("win", -1)
        val lose = intent.getIntExtra("lose", -1)
        val runtime = intent.getIntExtra("runtime", -1)
        val intent = Intent(this@StartRunActivity, RunActivity::class.java)

                "(roomName ${roomName})" +
                        " (name: $name)" +
                        " (level :$level)" +
                        " (image: $image)" +
                        " (win: $win)" +
                        " (lose: $lose)" +
                        " (runtime: $runtime)".logDebug(this@StartRunActivity)
                with(intent) {
                    putExtra("roomName", roomName)
                    putExtra("name", name)
                    putExtra("level", level)
                    putExtra("image", image)
                    putExtra("win", win)
                    putExtra("lose", lose)
                    putExtra("runtime", runtime)
                }
                startActivity(intent)
                finish()
            }

    fun runWithMe() {
        val matchData = intent.getParcelableExtra<RecordRunWithmeData>("matchData")
        val intent = Intent(this@StartRunActivity, RunMeActivity::class.java)
        intent.putExtra("matchData", matchData)
        startActivity(intent)
    }
}