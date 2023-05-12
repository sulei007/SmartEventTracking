package com.sulei.smarteventtracking.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * 曝光埋点采集 ViewPager
 * */
class SmartTraceViewPager : ViewPager {
    private var exposureTrackListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    init {
        //页面切换监听
        addOnPageChangeListener(object : OnPageChangeListener {
            private var hasPageChanged = false

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                hasPageChanged = true
            }

            override fun onPageScrollStateChanged(state: Int) {
                // ViewPager 切换完成，采集曝光信息
                if (state == SCROLL_STATE_IDLE && hasPageChanged) {
                    exposureTrackListener?.invoke()
                    hasPageChanged = false
                } else {
                    hasPageChanged = false
                }
            }
        })
    }

    /**
     * 设置采集曝光回调
     * */
    fun setExposureTrackListener(exposureTrackListener: (() -> Unit)) {
        this.exposureTrackListener = exposureTrackListener
    }
}