package com.wrxhard.ftravel.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wrxhard.ftravel.view.fragment.FoodFragment
import com.wrxhard.ftravel.view.fragment.LocationFragment

class HomeVPAdapter(frag: FragmentActivity): FragmentStateAdapter(frag) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0 -> LocationFragment()
            1 -> FoodFragment()
            else -> LocationFragment()
        }
    }

}