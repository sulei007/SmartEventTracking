package com.sulei.smarteventtracking.area

import android.view.View

/**
 * 判断子视图 水平方向上，是否在指定的区域
 * */
class HorizontalCorrectArea : ICorrectArea {
    override fun checkCorrectArea(child: View, areaLocation: IntArray): Boolean {
        val left = areaLocation[0]
        val right = areaLocation[2]

        val location = IntArray(2)
        //获得 child在屏幕上的坐标轴
        child.getLocationOnScreen(location)
        //child 左边坐标
        val viewLeft = location[0]
        //child 右边坐标
        val viewRight = location[0] + child.width
        if (viewRight - child.width / 3 <= left || viewLeft + child.width / 3 >= right) {
            return false
        }
        return true
    }
}