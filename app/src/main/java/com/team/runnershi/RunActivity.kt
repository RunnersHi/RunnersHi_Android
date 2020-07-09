package com.team.runnershi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_run.*


class RunActivity : AppCompatActivity() {
    private lateinit var runSelModel: RunSetViewModel
    private lateinit var thumView: View
    private val runSetViewModel = RunSetViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)


        initUi()

    }

    private fun initUi() {
        Glide.with(this).load(R.drawable.icon_unlock).into(btn_run_lock)
        ly_run_profile.x = 100f
        ly_run_profile.y = 20f

        runSelModel = RunSetViewModel()

        seekbar_run_record.setOnTouchListener { _, _ -> true }
        seekbar_run_record.progress = 25
        val width: Int = (seekbar_run_record.getWidth()
                - seekbar_run_record.getPaddingLeft()
                - seekbar_run_record.getPaddingRight())
        val thumbPos: Int = (seekbar_run_record.getPaddingLeft()
                + width
                * seekbar_run_record.getProgress()
                / seekbar_run_record.getMax())
        ly_run_profile.x = thumbPos.toFloat()

        seekbar_run_record.max = runSelModel.runTime.value ?: 30
//        Thread(Runnable {
//            for (i in 1 until seekbar_run_record.max) {
//                Thread.sleep(1000)
//                seekbar_run_record.progress += 1
//                val width: Int = (seekbar_run_record.getWidth()
//                        - seekbar_run_record.getPaddingLeft()
//                        - seekbar_run_record.getPaddingRight())
//                val thumbPos: Int = (seekbar_run_record.getPaddingLeft()
//                        + width
//                        * seekbar_run_record.getProgress()
//                        / seekbar_run_record.getMax())
//                ly_run_profile.x = thumbPos.toFloat()
//
//            }
//        }).start()


//                val seekBarX = seekbar_run_record.thumb.bounds.exactCenterX()
//                ly_run_profile.x = seekBarX
//        val seekBarX = seekbar_run_record.thumb.bounds.exactCenterX() + seekbar_run_record.thumb.bounds.width()

    }


}