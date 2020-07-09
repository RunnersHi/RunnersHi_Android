package com.team.runnershi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.runnershi.util.HorizontalItemDecorator
import com.team.runnershi.recycle.RankItemClass
import com.team.runnershi.recycle.RvRankHonorAdapter
import com.team.runnershi.recycle.RvRankLoseAdapter
import com.team.runnershi.recycle.RvRankMonthAdapter
import kotlinx.android.synthetic.main.fragment_rank.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankFragment : Fragment() {
    lateinit var rvRankMonthAdapter: RvRankMonthAdapter
    lateinit var rvRankHonorAdapter: RvRankHonorAdapter
    lateinit var rvRankLoseAdapter: RvRankLoseAdapter
    val month_datas = mutableListOf<RankItemClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Honor, Lose는 itemclass,viewHolder 수정해야함. (id값, win,lose data)
        //datas 부분도 서버 통신으로 변경해야함.
        rvRankMonthAdapter = RvRankMonthAdapter(view!!.context)
        rvRankHonorAdapter = RvRankHonorAdapter(view!!.context)
        rvRankLoseAdapter = RvRankLoseAdapter(view!!.context)
        rv_rank_month.adapter = rvRankMonthAdapter
        rv_rank_honor.adapter = rvRankHonorAdapter
        rv_rank_lose.adapter = rvRankLoseAdapter
        rvRankMonthAdapter.datas = month_datas
        rvRankHonorAdapter.datas = month_datas
        rvRankLoseAdapter.datas = month_datas
        loadMonthDatas()
        rvRankMonthAdapter.notifyDataSetChanged()
        rvRankHonorAdapter.notifyDataSetChanged()
        rvRankLoseAdapter.notifyDataSetChanged()


    }

    fun loadMonthDatas() {
        month_datas.apply {
            add(RankItemClass(id=1, rank = 1, profile = 1, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=2, rank = 2, profile = 3, nickname = "바보바보바보", data = "10" + "km"))
            add(RankItemClass(id=3, rank = 3, profile = 9, nickname = "챙챙이", data = "1032" + "km"))
            add(RankItemClass(id=4, rank = 4, profile = 1, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=5, rank = 5, profile = 3, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=6, rank = 6, profile = 9, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=7, rank = 7, profile = 1, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=8, rank = 8, profile = 4, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=9, rank = 9, profile = 9, nickname = "챙챙이", data = "10000" + "km"))
            add(RankItemClass(id=10, rank = 10, profile = 9, nickname = "챙챙이", data = "10000" + "km"))
        }
    }

}