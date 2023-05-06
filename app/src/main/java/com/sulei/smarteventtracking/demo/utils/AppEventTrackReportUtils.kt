package com.sulei.smarteventtracking.demo.utils

import android.content.Context
import com.sulei.smarteventtracking.action.IExecuteReportAction
import com.sulei.smarteventtracking.data.EventTrackBean
import com.sulei.smarteventtracking.demo.bean.AppEventTrackBean

/**
 * App，统一上报埋点的类
 * */
object AppEventTrackReportUtils {
    fun getExecuteReportAction(context: Context): IExecuteReportAction {
        val application = context.applicationContext
        return object : IExecuteReportAction {
            /**
             * 点击事件上报
             * */
            override fun executeClickReport(datas: ArrayList<EventTrackBean>) {
                datas.forEach {
                    if (it.extra is AppEventTrackBean) {
                        val data = it.extra as AppEventTrackBean
                        println("sulei Click $data")
                    }
                }
            }

            /**
             * 曝光事件上报
             * */
            override fun executeExposureReport(datas: ArrayList<EventTrackBean>) {
                datas.forEach {
                    if (it.extra is AppEventTrackBean) {
                        val data = it.extra as AppEventTrackBean
                        println("sulei Exposure $data")
                    }
                }
            }
        }
    }
}