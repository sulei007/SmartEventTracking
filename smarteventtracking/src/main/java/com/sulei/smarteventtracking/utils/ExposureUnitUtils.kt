package com.sulei.smarteventtracking.utils

import android.view.View
import android.view.ViewGroup
import com.sulei.smarteventtracking.R
import com.sulei.smarteventtracking.data.EventTrackBean

/**
 * 曝光单元工具类
 * */
object ExposureUnitUtils {
    /**
     * 绑定数据的tag_id
     * */
    private val Unit_Exposure_Tag_Id = R.id.unit_exposure_tag
    private val Unnecessary_Exposure_Tag_Id = R.id.unnecessary_exposure_tag

    /**
     * 是否已经曝光的标记
     * */
    private val Unit_Has_Exposure_Tag_Id = R.id.unit_has_exposure_tag

    private const val Unit_Has_Exposure_Tag = "Parent_Has_Exposure_Tag"
    private const val Unnecessary_Exposure_Tag = "Unnecessary_Exposure_Tag"

    /**
     * 通过给 unit 设置 tag，实现数据绑定
     * */
    fun injectExposureUnit(unit: View, bean: EventTrackBean) {
        unit.setTag(Unit_Exposure_Tag_Id, bean)
    }

    /**
     * 当前view 是否不需要曝光
     * */
    fun isUnnecessaryExposure(view: View): Boolean {
        return view.getTag(Unnecessary_Exposure_Tag_Id) != null
    }

    /**
     * 针对一些不需要曝光的view，进行注入，遍历时会跳过这些view
     * */
    fun injectUnnecessaryExposure(view: View) {
        view.setTag(Unnecessary_Exposure_Tag_Id, Unnecessary_Exposure_Tag)
    }

    /**
     * 针对一些注入了不需要曝光的view，取消注入
     * */
    fun cancelInjectUnnecessaryExposure(view: View) {
        view.setTag(Unnecessary_Exposure_Tag_Id, null)
    }

    /**
     * 是否是曝光最小单元
     * */
    fun isExposureUnit(view: View): Boolean {
        return view.getTag(Unit_Exposure_Tag_Id) != null
    }

    /**
     * 获取曝光最小单元绑定的埋点数据
     * */
    fun getUnitExposureBean(view: View): EventTrackBean? {
        if (isExposureUnit(view)) {
            val tag = view.getTag(Unit_Exposure_Tag_Id)
            if (tag is EventTrackBean) {
                return tag
            }
        }
        return null
    }

    /**
     * 是否已经曝光过了
     * */
    fun isUnitHasExposure(view: View): Boolean {
        val tag = view.getTag(Unit_Has_Exposure_Tag_Id)
        return tag != null
    }

    /**
     * 设置 unit 已经曝光标记
     * */
    fun setUnitHasExposure(view: View) {
        view.setTag(Unit_Has_Exposure_Tag_Id, Unit_Has_Exposure_Tag)
    }

    /**
     * 重置 unit 已经曝光标记
     * */
    fun resetUnitExposure(view: View) {
        view.setTag(Unit_Has_Exposure_Tag_Id, null)

    }
}