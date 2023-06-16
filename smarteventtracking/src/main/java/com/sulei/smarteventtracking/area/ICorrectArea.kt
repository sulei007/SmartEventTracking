package com.sulei.smarteventtracking.area

import android.view.View


interface ICorrectArea {
    /**
     * @param child 当前遍历到的 View
     * @param areaLocation 区域坐标 [left,top,right,bottom]

     * */
    fun checkCorrectArea(child: View, areaLocation: IntArray): Boolean
}