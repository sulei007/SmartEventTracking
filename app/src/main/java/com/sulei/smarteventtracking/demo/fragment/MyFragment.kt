package com.sulei.smarteventtracking.demo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sulei.smarteventtracking.demo.R
import com.sulei.smarteventtracking.demo.adapter.MyAdapter
import com.sulei.smarteventtracking.demo.bean.CartBean
import com.sulei.smarteventtracking.demo.bean.ItemBean
import com.sulei.smarteventtracking.demo.utils.AppEventTrackReportUtils
import com.sulei.smarteventtracking.demo.utils.ScreenUtils
import com.sulei.smarteventtracking.helper.EventTrackRegisterHelper
import com.sulei.smarteventtracking.helper.ExposureTrackHelper

/**
 * fragment
 * */
class MyFragment(val cId: Int, private val pageName: String) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterOne: MyAdapter
    private val handle = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    executeExposureTrack()
                }
            }
        })

        adapterOne = MyAdapter(pageName, ArrayList())
        recyclerView.adapter = adapterOne

        eventTrackRegister()

        //模拟网络请求。
        handle.postDelayed({
            setData()
        }, 2000)
    }

    private fun setData() {
        adapterOne.mList.clear()
        adapterOne.mList.apply {
            for (i in 1..50) {
                add(CartBean(ArrayList<ItemBean>().apply {
                    add(ItemBean(100 * cId + i, "我是 $pageName 的一孩子====  ${100 * cId + i}"))
                    add(ItemBean(1000 * cId + i, "我是 $pageName 的二孩子====  ${1000 * cId + i}"))
                }))
            }
        }
        adapterOne?.notifyDataSetChanged()
        //延迟50ms后，开始采集埋点，因为 notifyDataSetChanged 需要一定时间
        handle.postDelayed({ executeExposureTrack() }, 50)
    }

    override fun onDestroy() {
        super.onDestroy()
        handle.removeCallbacksAndMessages(null)
    }

    /**
     * 埋点采集
     * */
    private fun executeExposureTrack() {
        val location =
            intArrayOf(
                0,
                0,
                ScreenUtils.getScreenWidth(requireContext()),
                ScreenUtils.getScreenHeight(requireContext())
            )
        view?.let { ExposureTrackHelper.executeExposureTrackForViewGroup(it, location) }
    }

    /**
     * 注册要统计埋点的页面的生命周期
     * */
    private fun eventTrackRegister() {
        EventTrackRegisterHelper.registerLifecycleObserver(
            pageName,
            this,
            context?.let { AppEventTrackReportUtils.getExecuteReportAction(it) })
    }
}