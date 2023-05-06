package com.sulei.smarteventtracking.demo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sulei.smarteventtracking.demo.fragment.MyFragment

class ViewPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var mList = ArrayList<Fragment>()
    private var titles = arrayOf("第一个", "第二个", "第三个", "第四个", "第五个", "第六个")

    init {
        mList.add(MyFragment(1, "我是第一个"))
        mList.add(MyFragment(2, "我是第二个"))
        mList.add(MyFragment(3, "我是第三个"))
        mList.add(MyFragment(4, "我是第四个"))
        mList.add(MyFragment(5, "我是第五个"))
        mList.add(MyFragment(6, "我是第六个"))
    }


    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}