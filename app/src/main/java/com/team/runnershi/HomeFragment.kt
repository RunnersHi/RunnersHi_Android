package com.team.runnershi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import android.content.Context as Context1
import androidx.fragment.app.FragmentManager as FragmentManager1


class HomeFragment : Fragment() {
//    private var mBottomSheetListener : BottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        v.btn_home_runnow.setOnClickListener {
//            val bottom = BottomSheetFragment()
//            bottom.show()
        }
        return v
    }

//    override fun onOptionClick(text: String) {
//
//    }
}