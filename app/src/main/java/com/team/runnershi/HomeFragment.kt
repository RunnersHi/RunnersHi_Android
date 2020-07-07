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
    fun newInstance() : HomeFragment {
        return HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val bottom_sheet_dialog = BottomSheetDialog(this) 무시하세욤

        val view : View = inflater.inflate(R.layout.fragment_home, null)
        view.btn_home_runnow.setOnClickListener {
//            bottom_sheet_dialog.show() 무시하세욤
//            ((HomeActivity)getActivity()).replaceFragment(HomeFragment.newInstance())


        }

//        bottom_sheet_dialog.setContentView(view) 무시하세욤
//

        return view
        }

    }

