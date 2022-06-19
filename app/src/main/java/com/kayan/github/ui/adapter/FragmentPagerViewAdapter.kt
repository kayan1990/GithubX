package com.kayan.github.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Fragment的ViewPager适配器
 * Created by kayan
 * Date: 2022-06-18
 */

class FragmentPagerViewAdapter(private val fragmentList: List<Fragment>, supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

    override fun getCount(): Int {
        return fragmentList.size
    }


    override fun getItem(position: Int): Fragment {
       return fragmentList[position]
    }
}