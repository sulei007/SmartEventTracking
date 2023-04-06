package com.sulei.smarteventtracking.utils

import com.sulei.smarteventtracking.data.EventTrackBean

/**
 * 曝光事件，埋点数据bean的集合的工具类
 * */
object ExposureTrackCollectionUtils {
    /**
     * 根据页面纬度，维护需要曝光的数据列表
     * key 是页面page，value是待上报的数据
     * */
    private val waitReportMap = HashMap<String, ArrayList<EventTrackBean>>()

    /**
     * 根据页面纬度，维护已经曝光数据列表
     * key 是页面page，value是已经上报的数据
     * */
    private val reportedMap = HashMap<String, ArrayList<EventTrackBean>>()

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
    fun addExposureTrackBean(pageName: String, bean: EventTrackBean) {
        if (isBeanReported(pageName, bean)) {//已经上报过，不能重复添加
            return
        }

        val datas = waitReportMap[pageName]
        if (datas != null && !datas.contains(bean)) {
            datas.add(bean)
        }
    }

    /**
     * 返回当前页面需要上报的数据
     * */
    fun getExposureTrackListByPage(pageName: String): ArrayList<EventTrackBean>? {
        return waitReportMap[pageName]
    }

    /**
     * 移除当前页面上报的数据
     * */
    fun removeExposureTrackListByPage(pageName: String) {
        waitReportMap.remove(pageName)
        reportedMap.remove(pageName)
    }

    /**
     * 执行完上报，需要将 waitReportMap的内容， 移动到 reportedMap
     * */
    fun moveExposureTrackListFromWaitToReported(pageName: String) {
        val list = waitReportMap[pageName]
        if (!list.isNullOrEmpty()) {
            var datas = reportedMap[pageName]
            if (datas == null) {
                datas = ArrayList()
                reportedMap[pageName] = datas
            }
            datas.addAll(list)
            waitReportMap[pageName] = ArrayList()
        }
    }

    /**
     * 当前bean是否已经上报过了
     * */
    private fun isBeanReported(pageName: String, bean: EventTrackBean): Boolean {
        val reported = reportedMap[pageName]
        if (!reported.isNullOrEmpty() && reported.contains(bean)) {
            return true
        }
        return false
    }
}