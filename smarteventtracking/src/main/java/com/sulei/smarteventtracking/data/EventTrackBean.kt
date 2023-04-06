package com.sulei.smarteventtracking.data

/**
 * 埋点数据bean
 * */
data class EventTrackBean(
    val uniqueId: String,//数据bean的唯一标记，根据唯一标记去重
    val pageName:String,//当前埋点数据，属于那个页面，这个值必须和EventTrackRegisterHelper注册时的值保持一致
    val extra: Any//业务方，自定义的上报内容
) {
    override fun equals(other: Any?): Boolean {
        if (other is EventTrackBean) {
            return uniqueId == other.uniqueId
        }
        return false
    }

    override fun hashCode(): Int {
        return uniqueId.hashCode()
    }
}