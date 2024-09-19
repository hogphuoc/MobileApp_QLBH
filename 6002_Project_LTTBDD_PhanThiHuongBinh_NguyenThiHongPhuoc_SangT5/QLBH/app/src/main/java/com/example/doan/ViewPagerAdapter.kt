package com.example.doan

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: HomePageFragment): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int=2

    override fun createFragment (position: Int): Fragment {
        return when (position){
            0 -> QuanLyFragment()
            1 -> TaiKhoanFragment()
            else -> QuanLyFragment()
        }
    }
}