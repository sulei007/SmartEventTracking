package com.sulei.smarteventtracking.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sulei.smarteventtracking.utils.ExposureTrackCollectionUtils
import com.sulei.smarteventtracking.action.IExecuteExposureAction

/**
 * 当前页面的生命周期监听
 * */
class TrackLifecycleObserver(
    private val pageName: String,
    private var action: IExecuteExposureAction?
) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        try {
            if (event == Lifecycle.Event.ON_PAUSE) {
                executeAction()
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                action = null
                removeAction()
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
        ExposureTrackCollectionUtils.getExposureTrackListByPage(pageName)?.let {
            action?.executeReport(it)
            ExposureTrackCollectionUtils.moveExposureTrackListFromWaitToReported(pageName)
        }
    }

    /**
     * 页面销毁的时候，清空数据
     * */
    private fun removeAction() {
        ExposureTrackCollectionUtils.removeExposureTrackListByPage(pageName)
    }
}