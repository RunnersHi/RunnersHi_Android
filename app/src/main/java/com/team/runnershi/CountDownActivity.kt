package com.team.runnershi

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
                val intent = Intent(this@CountDownActivity, StartRunActivity::class.java)
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

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
    }

    private fun GoStartRunActivity() {

    }
}