package com.team.runnershi.countdown

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.runnershi.R
import com.team.runnershi.StartRunActivity
import com.team.runnershi.extension.logDebug
import kotlinx.android.synthetic.main.activity_count_down.*
import android.content.Intent as Intent


class CountDownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        initUi()
    }

    private fun initUi() {
        ani_coundown_number.playAnimation()
        ani_coundown_number.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                val roomName = intent.getStringExtra("roomName")
                val name = intent.getStringExtra("name")
                val level = intent.getIntExtra("level", -1)
                val image = intent.getIntExtra("image", -1)
                val win = intent.getIntExtra("win", -1)
                val lose = intent.getIntExtra("lose", -1)
                val runtime = intent.getIntExtra("runtime", -1)

                "(roomName ${roomName}) (name: $name) (level :$level) (image: $image) (win: $win) (lose: $lose) (runtime: $runtime)".logDebug(this@CountDownActivity)

                val intent = Intent(this@CountDownActivity, StartRunActivity::class.java)
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

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
    }

}