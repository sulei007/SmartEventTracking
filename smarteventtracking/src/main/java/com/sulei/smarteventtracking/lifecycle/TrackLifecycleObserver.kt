package com.sulei.smarteventtracking.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sulei.smarteventtracking.utils.ExposureTrackCollectionUtils
import com.sulei.smarteventtracking.action.IExecuteReportAction
import com.sulei.smarteventtracking.helper.EventTrackRegisterHelper
import com.sulei.smarteventtracking.utils.ClickTrackCollectionUtils

/**
 * 当前页面的生命周期监听
 * */
class TrackLifecycleObserver(
    private val pageName: String,
    private var action: IExecuteReportAction?
) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        try {
            if (event == Lifecycle.Event.ON_PAUSE) {
                executeAction()
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                action = null
                removeAction()
                EventTrackRegisterHelper.removeTopPageName()
                source.lifecycle.removeObserver(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 页面暂停的时候，执行上报
     * */
    private fun executeAction() {
        //曝光上报
        ExposureTrackCollectionUtils.getExposureTrackListByPage(pageName)?.let {
            action?.executeExposureReport(it)
            ExposureTrackCollectionUtils.moveExposureTrackListFromWaitToReported(pageName)
        }

        //点击上报
        ClickTrackCollectionUtils.getClickTrackListByPage(pageName)?.let {
            action?.executeClickReport(it)
            ClickTrackCollectionUtils.removeClickTrackListByPage(pageName)
        }
    }

    /**
     * 页面销毁的时候，清空数据
     * */
    private fun removeAction() {
        ExposureTrackCollectionUtils.removeExposureTrackListByPage(pageName)
        ClickTrackCollectionUtils.removeClickTrackListByPage(pageName)
    }
}