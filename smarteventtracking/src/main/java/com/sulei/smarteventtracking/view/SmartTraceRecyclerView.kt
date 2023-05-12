package com.sulei.smarteventtracking.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.RecyclerView

/**
 * 曝光埋点采集 RecyclerView
 * */
class SmartTraceRecyclerView : RecyclerView {
    private var exposureTrackListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }


    constructor(context: Context, attrs: AttributeSet, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    init {
        //滚动监听
        addOnScrollListener(object : OnScrollListener() {
            private var mState = -1

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // RecyclerView 滚动停止或者拖拽中，采集曝光信息
                mState = newState
                if (mState == SCROLL_STATE_IDLE) {
                    exposureTrackListener?.invoke()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mState == SCROLL_STATE_DRAGGING) {
                    exposureTrackListener?.invoke()
                }
            }
        })
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        //数据变更监听
        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                //adapter 设置完成数据后，需要时间渲染，所以延时 60ms采集埋点
                postDelayed({
                    exposureTrackListener?.invoke()
                }, 60)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                onChanged()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                onChanged()
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