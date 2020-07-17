package com.team.runnershi.feature.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class VpOnBoardAdapter(fm: FragmentManager, private val page_count: Int) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        val viewFragment = ViewFragment()
        val bundle = Bundle(page_count)
        bundle.putInt("id",position)
        viewFragment.arguments = bundle
        return viewFragment
    }

    override fun getCount(): Int = page_count
}