package com.team.runnershi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.ly_homebattle_other
import kotlinx.android.synthetic.main.fragment_home.view.*


class BottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

//        view.ly_homebattle_other.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//                (activity as HomeActivity).click_bottomsheet_other()
//            }
//
//        })
//
//        view.ly_homebattle_me.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//                (activity as HomeActivity).click_bottomsheet_me()
//            }
//
//        })


        return view
    }

    override fun onClick(view: View?) {


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let { ly_homebattle_other.setOnClickListener {

            val intent1 = Intent(context, GoalActivity::class.java)
            startActivity(intent1)
            dismiss()
        } }



        activity?.let { ly_homebattle_me.setOnClickListener {
            val intent2 = Intent(context, GoalRunMeActivity::class.java)
            startActivity(intent2)
            dismiss()
        } }
    }


}