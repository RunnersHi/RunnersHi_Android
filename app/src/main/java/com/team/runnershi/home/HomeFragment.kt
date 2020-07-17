package com.team.runnershi.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.runnershi.R

import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        view.btn_home_runnow.setOnClickListener(object : View.OnClickListener {
//
            override fun onClick(p0: View?) {
                (activity as HomeActivity).showDialog()    //()부분은 HomeActivity가 된다.

            }
        })

        return view
    }
}



