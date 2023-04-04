package com.sulei.smarteventtracking.action

import com.sulei.smarteventtracking.data.EventTrackBean

/**
 * 不同业务方，执行上报操作
 * */
interface IExecuteReportAction {
    fun  executeExposureReport(datas: ArrayList<EventTrackBean>)

    fun  executeClickReport(datas: ArrayList<EventTrackBean>)
}