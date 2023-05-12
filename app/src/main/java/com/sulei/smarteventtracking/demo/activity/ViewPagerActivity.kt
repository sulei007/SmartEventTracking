package com.sulei.smarteventtracking.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sulei.smarteventtracking.demo.R
import com.sulei.smarteventtracking.demo.adapter.ViewPagerAdapter
import com.sulei.smarteventtracking.demo.databinding.ActivityViewPagerBinding
import com.sulei.smarteventtracking.demo.utils.ScreenUtils
import com.sulei.smarteventtracking.helper.ExposureTrackHelper

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_view_pager)
        mBinding.apply {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
            //tabLayout 不需要曝光
            ExposureTrackHelper.injectUnnecessaryExposure(tabLayout)
            viewPager.setExposureTrackListener {
                executeExposureTrack()
            }
        }
    }

    private fun executeExposureTrack() {
        mBinding.apply {
            val location =
                intArrayOf(
                    0,
                    0,
                    ScreenUtils.getScreenWidth(this@ViewPagerActivity),
                    ScreenUtils.getScreenHeight(this@ViewPagerActivity)
                )
            ExposureTrackHelper.executeExposureTrackForView(root, location)
        }
    }
}