package com.sulei.smarteventtracking.demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sulei.smarteventtracking.demo.R
import com.sulei.smarteventtracking.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            setOnRecyclerView {
                startActivity(Intent(this@MainActivity, RecyclerViewActivity::class.java))
            }

            setOnViewPager {
                startActivity(Intent(this@MainActivity, ViewPagerActivity::class.java))
            }
        }
    }
}