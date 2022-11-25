package com.example.chien_an_chen_myruns4

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentStateAdapter(activity: FragmentActivity, fragmentList: ArrayList<Fragment>): FragmentStateAdapter(activity) {

    private var fragmentList: ArrayList<Fragment>

    init{
        this.fragmentList = fragmentList
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}