package com.sulei.smarteventtracking.utils

import com.sulei.smarteventtracking.data.ExposureTrackBean

/**
 * 曝光bean的集合的工具类
 * */
object ExposureTrackCollectionUtils {
    /**
     * 根据页面纬度，维护需要曝光的数据列表
     * key 是页面page，value是待上报的数据
     * */
    private val waitReportMap = HashMap<String, ArrayList<ExposureTrackBean>>()

    /**
     * 根据页面纬度，维护已经曝光数据列表
     * key 是页面page，value是已经上报的数据
     * */
    private val reportedMap = HashMap<String, ArrayList<ExposureTrackBean>>()

    /**
     * 添加曝光bean
     * */
    fun addExposureTrackBean(pageName: String, bean: ExposureTrackBean) {
        if (isBeanReported(pageName, bean)) {//已经上报过，不能重复添加
            return
        }

        var datas = waitReportMap[pageName]
        if (datas == null) {
            datas = ArrayList()
            waitReportMap[pageName] = datas
        }
        if (!datas.contains(bean)) {
            datas.add(bean)
        }
    }

    /**
     * 返回当前页面的需要曝光数据
     * */
    fun getExposureTrackListByPage(pageName: String): ArrayList<ExposureTrackBean>? {
        return waitReportMap[pageName]
    }

    /**
     * 移除当前页面的曝光数据
     * */
    fun removeExposureTrackListByPage(pageName: String) {
        waitReportMap.remove(pageName)
        reportedMap.remove(pageName)
    }

    /**
     * 执行完上报，需要将 waitReportMap的内容， 移动到 reportedMap
     * */
    fun moveExposureTrackListFromWaitToReported(pageName: String) {
        waitReportMap.remove(pageName)?.let {
            var datas = reportedMap[pageName]
            if (datas == null) {
                datas = ArrayList()
                reportedMap[pageName] = datas
            }
            datas.addAll(it)
        }
    }

    /**
     * 当前bean是否已经上报过了
     * */
    private fun isBeanReported(pageName: String, bean: ExposureTrackBean): Boolean {
        val reported = reportedMap[pageName]
        if (!reported.isNullOrEmpty() && reported.contains(bean)) {
            return true
        }
        return false
    }
}