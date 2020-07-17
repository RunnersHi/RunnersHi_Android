package com.team.runnershi.feature.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.team.runnershi.feature.login.LoginActivity
import com.team.runnershi.R
import com.team.runnershi.extension.newStartActivity
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        vp_on_board.adapter = VpOnBoardAdapter(
            supportFragmentManager,
            4
        )
        vp_on_board.offscreenPageLimit = 3
        indicator_on_board.setupWithViewPager(vp_on_board)

        vp_on_board.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if(position<3){
                    btn_on_board_next.text = "NEXT"
                }else{
                    btn_on_board_next.text = "START"
                }
            }
        })

        btn_on_board_next.setOnClickListener {
            var position = vp_on_board.currentItem
            if(position<3){
                vp_on_board.setCurrentItem(position+1)
            }else{
                newStartActivity(LoginActivity::class.java)
                finish()
            }
        }
    }
}