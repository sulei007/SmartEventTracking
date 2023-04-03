package com.sulei.smarteventtracking.helper

import android.view.View
import com.sulei.smarteventtracking.action.IExecuteClickAction
import com.sulei.smarteventtracking.utils.ExposureUnitUtils

/**
 * 埋点点击工具类，业务方只需要调用该类即可
 * */
object ClickTrackHelper {
    
    /**
     * 调用点击方法
     * @param view 已经注入了曝光bean的最小曝光单元
     * */
    fun executeClickTrack(view: View, action: IExecuteClickAction?) {
        ExposureUnitUtils.getUnitExposureBean(view)?.let {
            action?.executeReport(it)
        }
    }
}