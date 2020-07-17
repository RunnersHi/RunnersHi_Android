package com.team.runnershi.rank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.runnershi.R
import com.team.runnershi.extension.customEnqueue
import com.team.runnershi.extension.logDebug
import com.team.runnershi.network.RequestToServer
import com.team.runnershi.util.PrefInit.Companion.prefs
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment : Fragment() {
    val requestToServer = RequestToServer
    lateinit var rvRankMonthAdapter: RvRankMonthAdapter
    lateinit var rvRankHonorAdapter: RvRankHonorAdapter
    lateinit var rvRankLoseAdapter: RvRankLoseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadMonthDatas() //month
        loadHonorDatas() //honor
        loadLoseDatas() //lose

    }

    fun loadMonthDatas() {
        val token = prefs.getString("token",null)
        requestToServer.service.requestRunner(token).customEnqueue(
            onFailure = { call, t ->
                context?.let { "requestRunner onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            rvRankMonthAdapter =
                                RvRankMonthAdapter(
                                    view!!.context,
                                    body!!.result
                                )
                            rv_rank_month.adapter = rvRankMonthAdapter
                            rvRankMonthAdapter.notifyDataSetChanged()
                        } else {
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }

    fun loadHonorDatas() {
        val token = prefs.getString("token",null)
        requestToServer.service.requestWinner(token).customEnqueue(
            onFailure = { call, t ->
                context?.let { "requestHonor onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            rvRankHonorAdapter =
                                RvRankHonorAdapter(
                                    view!!.context,
                                    body!!.result
                                )
                            rv_rank_honor.adapter = rvRankHonorAdapter
                            rvRankHonorAdapter.notifyDataSetChanged()
                        } else {
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }

    fun loadLoseDatas() {
        val token = prefs.getString("token",null)
        requestToServer.service.requestLoser(token).customEnqueue(
            onFailure = { call, t ->
                context?.let { "requestLoser onFailure msg = ${t.message}".logDebug(it) }
            },
            onResponse = { call, r ->
                if (r.isSuccessful) {
                    val body = r.body()
                    if (body?.status == 200) {
                        if (body?.success) {
                            rvRankLoseAdapter =
                                RvRankLoseAdapter(
                                    view!!.context,
                                    body!!.result
                                )
                            rv_rank_lose.adapter = rvRankLoseAdapter
                            rvRankLoseAdapter.notifyDataSetChanged()
                        } else {
                        }
                    }
                } else {
                    Log.d(
                        "TAG",
                        "requestConfirm onSuccess but response code is not 200 ~ 300 " +
                                "(status code:${r.code()}) " +
                                "(message: ${r.message()})" +
                                "(errorBody: ${r.errorBody()})"
                    )
                }
            }
        )
    }

}