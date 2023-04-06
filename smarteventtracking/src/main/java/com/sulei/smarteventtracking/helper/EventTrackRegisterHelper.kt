package com.sulei.smarteventtracking.helper

import androidx.lifecycle.LifecycleOwner
import com.sulei.smarteventtracking.action.IExecuteReportAction
import com.sulei.smarteventtracking.lifecycle.TrackLifecycleObserver
import java.util.Stack

/**
 * 注册执行上报的页面，必须先注册，然后再调用 ClickTrackHelper 和 ExposureTrackHelper 的方法
 * */
object EventTrackRegisterHelper {
    private val pageNameStack = Stack<String>()

    /**
     * 执行上报的页面，需要先注册下，否则不会执行曝光上报操作和点击上报操作
     * @param pageName 页面唯一标示，可以使用 java.class.name
     * */
    fun registerLifecycleObserver(
        pageName: String, lifecycleOwner: LifecycleOwner, action: IExecuteReportAction?
    ) {
        lifecycleOwner.lifecycle.addObserver(TrackLifecycleObserver(pageName, action))
        pushPageName(pageName)
    }

    /**
     * 往栈中插入内容
     * */
    private fun pushPageName(pageName: String) {
        pageNameStack.push(pageName)
    }

    /**
     * 获取栈顶的内容
     * */
    fun getTopPageName(): String {
        println("EventTrack Stack List ---> $pageNameStack")
        if (pageNameStack.empty()) {
            return ""
        }
        return pageNameStack.peek()
    }

    /**
     * 移除栈顶的内容
     * */
    fun removeTopPageName() {
        if (!pageNameStack.empty()) {
            pageNameStack.pop()
        }
    }
}