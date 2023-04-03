package com.sulei.smarteventtracking.area

import android.view.View

/**
 * 判断子视图 垂直方向上，是否在指定的区域
 * */
class VerticalCorrectArea : ICorrectArea {
    override fun checkCorrectArea(child: View, areaLocation: IntArray): Boolean {
        val top = areaLocation[1]
        val bottom = areaLocation[3]
        val location = IntArray(2)
        //获得 child在屏幕上的坐标轴
        child.getLocationOnScreen(location)
        //child 顶部坐标
        val viewTop = location[1]
        //child 底部坐标
        val viewBottom = location[1] + child.height
        if (viewBottom <= top || viewTop >= bottom) {
            return false
        }
        return true
    }
}