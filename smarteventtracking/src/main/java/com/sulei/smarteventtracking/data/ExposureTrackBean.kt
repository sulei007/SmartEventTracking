package com.sulei.smarteventtracking.data

/**
 * 曝光数据bean
 * */
data class ExposureTrackBean(
    val uniqueId: String,//数据bean的唯一标记，根据唯一标记去重
    val extra: Any//业务方，自定义的上报内容
) {
    override fun equals(other: Any?): Boolean {
        if (other is ExposureTrackBean) {
            return uniqueId == other.uniqueId
        }
        return false
    }

    override fun hashCode(): Int {
        return uniqueId.hashCode()
    }
}