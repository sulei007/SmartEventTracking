package com.sulei.smarteventtracking.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sulei.smarteventtracking.demo.R
import com.sulei.smarteventtracking.demo.databinding.ActivityRecyclerViewBinding
import com.sulei.smarteventtracking.demo.fragment.MyFragment

class RecyclerViewActivity : AppCompatActivity() {
    private val pageName = this.javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityRecyclerViewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_recycler_view)
        mBinding.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_content, MyFragment(1, pageName))
                .commitAllowingStateLoss()
        }
    }
}