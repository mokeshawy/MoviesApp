package com.example.moviesapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle


class ViewPagerAdapter( items : ArrayList<Fragment> , fm: FragmentManager , lifecycle: Lifecycle) : FragmentStateAdapter(fm , lifecycle) {

    val fragmentItems = items
    override fun getItemCount(): Int {

        return fragmentItems.size
    }
    override fun createFragment(position: Int): Fragment {

        return fragmentItems[position]
    }
}