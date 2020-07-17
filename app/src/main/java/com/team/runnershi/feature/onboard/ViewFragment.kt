package com.team.runnershi.feature.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.R
import kotlinx.android.synthetic.main.fragment_view.*

class ViewFragment : Fragment() {
    var view_id = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view_id = arguments!!.getInt("id")
        init()
    }

    private fun init() {
        when (view_id) {
            0 -> {
                imgv_on_board.setImageResource(R.drawable.onboard_1_illust)
                tv_on_board.text = "언제 어디서든 다른 러너와 \n" +
                        "함께 경쟁하며 달려 보세요."
            }
            1 -> {
                imgv_on_board.setImageResource(R.drawable.onboard_2_illust)
                tv_on_board.text = "어제의 나와의 러닝 경쟁을 통해\n" +
                        "자신의 한계를 극복해 보세요."
            }
            2 -> {
                imgv_on_board.setImageResource(R.drawable.onboard_3_illust)
                tv_on_board.text = "모든 러닝 기록을\n" +
                        "한눈에 파악해보세요."
            }
            3 -> {
                imgv_on_board.setImageResource(R.drawable.onboard_4_illust)
                tv_on_board.text = "이번 달의 러너가\n" +
                        "되어보세요."
            }
        }

    }
}