package com.sulei.smarteventtracking.helper

import android.view.View
import com.sulei.smarteventtracking.utils.ClickTrackCollectionUtils
import com.sulei.smarteventtracking.utils.ExposureUnitUtils

/**
 * 埋点点击工具类，业务方只需要调用该类即可
 * 一个 view必须已经注入了埋点数据bean，然后才能执行点击埋点
 * */
object ClickTrackHelper {

    /**
     * 调用点击方法
     * @param view 已经注入了埋点数据bean的最小曝光单元
     * @param pageName 必须和 EventTrackRegisterHelper registerLifecycleObserver 方法的 pageName 保持一致
     * */
    fun executeClickTrack(pageName: String, view: View) {
        ExposureUnitUtils.getUnitExposureBean(view)?.let {
            ClickTrackCollectionUtils.addClickTrackBean(pageName, it)
        }
    }
}