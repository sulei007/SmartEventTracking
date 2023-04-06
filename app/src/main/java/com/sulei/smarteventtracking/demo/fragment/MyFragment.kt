package com.sulei.smarteventtracking.demo.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sulei.smarteventtracking.demo.R
import com.sulei.smarteventtracking.demo.adapter.MyAdapter
import com.sulei.smarteventtracking.demo.bean.Info
import com.sulei.smarteventtracking.helper.ExposureTrackHelper

class MyFragment(val cId: Int, val name: String) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterOne: MyAdapter
    val pageName = ""
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

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        adapterOne = MyAdapter(name, ArrayList<Info>())
        recyclerView.adapter = adapterOne

        handle.postDelayed({
            setData()
            handle.postDelayed({ executeExposureTrack() }, 50)
        }, 2000)
    }

    private fun setData() {
        //模拟网络请求。
        adapterOne.mList.clear()
        adapterOne.mList.apply {
            for (i in 1..50) {
                add(Info(i, "$name ====  $i", ArrayList<Info>().apply {
                    add(Info(100 * cId + i, "我是 $name 的一孩子====  ${100 * cId + i}", null))
                    add(Info(1000 * cId + i, "我是 $name 的二孩子====  ${1000 * cId + i}", null))
                }))
            }
        }
        adapterOne?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        handle.removeCallbacksAndMessages(null)
    }

    private fun executeExposureTrack() {
        val location =
            intArrayOf(0, 0, getScreenWidth(requireContext()), getScreenHeight(requireContext()))
        view?.let { ExposureTrackHelper.executeExposureTrackForViewGroup(it, location) }
    }


    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService("window") as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService("window") as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}