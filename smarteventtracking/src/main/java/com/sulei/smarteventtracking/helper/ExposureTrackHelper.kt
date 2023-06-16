package com.sulei.smarteventtracking.helper

import android.view.View
import android.view.ViewGroup
import com.sulei.smarteventtracking.area.HorizontalCorrectArea
import com.sulei.smarteventtracking.area.ICorrectArea
import com.sulei.smarteventtracking.area.VerticalCorrectArea
import com.sulei.smarteventtracking.data.EventTrackBean
import com.sulei.smarteventtracking.utils.ExposureTrackCollectionUtils
import com.sulei.smarteventtracking.utils.ExposureUnitUtils

/**
 * 埋点曝光工具类，业务方只需要调用该类即可
 * */
object ExposureTrackHelper {
    /**
     * 判断一个ViewGroup 是否在指定区域内的逻辑集合，业务方可以调用 extendCorrectArea 方法进行扩展。
     * 默认有如下区域判断：
     * 1，垂直方向判断
     * 2，水平方向判断
     * */
    private val correctAreaLists = ArrayList<ICorrectArea>().apply {
        add(VerticalCorrectArea())
        add(HorizontalCorrectArea())
    }

    /**
     * 附加方法，业务方可以根据具体场景，自定义扩展判断区域逻辑。
     * 该方法必须在 executeExposureTrackForViewGroup 之前调用
     * */
    fun extendCorrectArea(extendCorrectArea: ICorrectArea) {
        correctAreaLists.add(extendCorrectArea)
    }

    /**
     * 第一步，曝光注入
     * @param unit 曝光的最小单元
     * @param bean 埋点数据bean
     * */
    fun injectExposureUnit(unit: View, bean: EventTrackBean) {
        ExposureUnitUtils.injectExposureUnit(unit, bean)
        ExposureUnitUtils.resetUnitExposure(unit)
    }

    /**
     * 第二步，执行View的曝光
     * @param location 区域坐标[left,top,right,bottom]
     * */
    fun executeExposureTrackForView(view: View, location: IntArray) {
        val startTime = System.currentTimeMillis()
        executeExposureTrack(view, location)
        val endTime = System.currentTimeMillis()
        println("SmartEventTrack 本次曝光采集耗时====== ${endTime - startTime}")
    }

    /**
     * 针对一些不需要曝光的view，进行注入，遍历时会跳过这些view
     * */
    fun injectUnnecessaryExposure(view: View) {
        ExposureUnitUtils.injectUnnecessaryExposure(view)
    }

    /**
     * 针对一些注入了不需要曝光的view，取消注入
     * */
    fun cancelInjectUnnecessaryExposure(view: View) {
        ExposureUnitUtils.cancelInjectUnnecessaryExposure(view)
    }


    /**
     * 执行遍历
     * 筛选在展示区间内所有满足条件的曝光单元，然后将埋点数据bean加入到页面纬度的曝光列表里
     * @param location 区域坐标[left,top,right,bottom]
     * */
    private fun executeExposureTrack(view: View, location: IntArray) {
        try {
            //不可见，不执行遍历
            if (view.visibility != View.VISIBLE) {
                return
            }

            //当前view 不需要曝光，不执行遍历
            if (ExposureUnitUtils.isUnnecessaryExposure(view)) {
                return
            }

            //当前view 已经曝光过，不执行遍历
            if (ExposureUnitUtils.isUnitHasExposure(view)) {
                return
            }

            //有任何一个区域判断不满足，则不执行遍历
            correctAreaLists.forEach {
                if (!it.checkCorrectArea(view, location)) {
                    return
                }
            }

            //是最小埋点单元，所以找到它后，则不执行遍历
            if (ExposureUnitUtils.isExposureUnit(view)) {
                ExposureUnitUtils.getUnitExposureBean(view)?.let {
                    if (ExposureTrackCollectionUtils.addExposureTrackBean(it.pageName, it)) {
                        ExposureUnitUtils.setUnitHasExposure(view)
                    }
                }
                return
            }

            //遍历 ViewGroup
            if (view is ViewGroup) {
                for (index: Int in 0 until view.childCount) {
                    //遍历ViewGroup的子view
                    val child = view.getChildAt(index)
                    executeExposureTrack(child, location)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}