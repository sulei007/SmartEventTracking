package com.sulei.smarteventtracking.action

import com.sulei.smarteventtracking.data.ExposureTrackBean

/**
 * 不同业务方，执行上报操作
 * */
interface IExecuteClickAction {
    fun  executeReport(datas: ExposureTrackBean)
}