package com.sulei.smarteventtracking.area

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * 如果是 child 的父类是viewpager，需要单独在水平方向上做处理做处理
 * */
class ViewPagerCorrectArea : ICorrectArea {
    override fun checkCorrectArea(child: View, areaLocation: IntArray): Boolean {
        val parent = child.parent
        if (parent is ViewPager) {
            val childLocation = IntArray(2)
            //获得 child在屏幕上的坐标轴
            child.getLocationOnScreen(childLocation)

            val parentLocation = IntArray(2)
            //获得 child在屏幕上的坐标轴
            parent.getLocationOnScreen(parentLocation)

            return childLocation[0] == parentLocation[0]
        }
        return true
    }
}