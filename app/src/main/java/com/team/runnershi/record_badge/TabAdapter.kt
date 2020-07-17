package com.team.runnershi.record_badge

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return RecFragment()
            else -> return BadgeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {return "러닝기록"}
            else -> {return "러닝뱃지"}
        }
    }

    override fun getCount() = 2

}