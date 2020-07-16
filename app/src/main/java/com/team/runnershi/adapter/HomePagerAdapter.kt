package com.team.runnershi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.team.runnershi.myprofile.MyProfileFragment
import com.team.runnershi.record_badge.RecBadgeFragment
import com.team.runnershi.home.HomeFragment
import com.team.runnershi.rank.RankFragment

class HomePagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> RecBadgeFragment()
            2 -> RankFragment()
            else -> MyProfileFragment()
        }
    }

    override fun getCount() = 4
}