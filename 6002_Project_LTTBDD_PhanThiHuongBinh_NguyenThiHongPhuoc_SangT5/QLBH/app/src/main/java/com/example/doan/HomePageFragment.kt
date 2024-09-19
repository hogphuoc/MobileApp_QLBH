package com.example.doan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {
    private lateinit var tablayout: TabLayout
    private lateinit var viewpager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=  inflater.inflate(R.layout.home_page_fragment, container, false)

        tablayout = view.findViewById(R.id.tablayoutmenu)
        viewpager = view.findViewById(R.id.viewpager)

        val adapter = ViewPagerAdapter(this)
        viewpager.adapter= adapter


        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Quản lý"
                1 -> "Tài khoản"
                else -> "Quản lý"
            }
        }.attach()

        return view
    }

}