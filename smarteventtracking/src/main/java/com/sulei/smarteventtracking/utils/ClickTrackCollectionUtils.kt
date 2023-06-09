package com.sulei.smarteventtracking.utils

import com.sulei.smarteventtracking.data.EventTrackBean

/**
 * 点击事件，埋点数据bean操作合集
 * */
object ClickTrackCollectionUtils {
    /**
     * 根据页面纬度，维护需要上报的数据列表
     * key 是页面page，value是待上报的数据
     * */
    private val waitReportMap = HashMap<String, ArrayList<EventTrackBean>>()


    /**
     * 调用 EventTrackRegisterHelper 注册时，初始化 map
     * */
    fun initWaitReportMap(pageName: String) {
        if (waitReportMap[pageName] == null) {
            waitReportMap[pageName] = ArrayList()
        }
    }

    /**
     * 添加埋点数据bean
     * */
    fun addClickTrackBean(pageName: String, bean: EventTrackBean) {
        val datas = waitReportMap[pageName]
        datas?.add(bean)
    }

    /**
     * 返回当前页面需要上报的数据
     * */
    fun getClickTrackListByPage(pageName: String): ArrayList<EventTrackBean>? {
        return waitReportMap[pageName]
    }

    /**
     * 清空当前页面的上报数据
     * */
    fun clearClickTrackListByPage(pageName: String) {
        val list = waitReportMap[pageName]
        if (!list.isNullOrEmpty()) {
            waitReportMap[pageName] = ArrayList()
        }
    }

    /**
     * 移除当前页面的上报数据
     * */
    fun removeClickTrackListByPage(pageName: String) {
        waitReportMap.remove(pageName)
    }
}