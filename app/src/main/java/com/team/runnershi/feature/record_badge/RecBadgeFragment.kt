package com.team.runnershi.feature.record_badge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.R
import kotlinx.android.synthetic.main.fragment_recbadge.*


class RecBadgeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        return inflater.inflate(R.layout.fragment_recbadge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureTopNavigation()
    }

    private fun configureTopNavigation() {
        vp_rec_rec.adapter =
            TabAdapter(childFragmentManager)
        vp_rec_rec.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(vp_rec_rec)
    }

}