package com.sulei.smarteventtracking.helper

import androidx.lifecycle.LifecycleOwner
import com.sulei.smarteventtracking.action.IExecuteReportAction
import com.sulei.smarteventtracking.lifecycle.TrackLifecycleObserver

/**
 * 注册执行上报的页面，必须先注册，然后再调用 ClickTrackHelper 和 ExposureTrackHelper 的方法
 * */
object EventTrackRegisterHelper {
    /**
     * 执行上报的页面，需要先注册下，否则不会执行曝光上报操作和点击上报操作
     * @param pageName 页面唯一标示，可以使用 java.class.name
     * */
    fun registerLifecycleObserver(
        pageName: String, lifecycleOwner: LifecycleOwner, action: IExecuteReportAction?
    ) {
        lifecycleOwner.lifecycle.addObserver(TrackLifecycleObserver(pageName, action))
    }
}