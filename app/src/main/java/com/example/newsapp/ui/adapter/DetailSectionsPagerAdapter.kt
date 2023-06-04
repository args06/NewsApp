package com.example.newsapp.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.helper.Constant.ARG_SECTION_NUMBER
import com.example.newsapp.ui.view.TabFragment

class DetailSectionsPagerAdapter(
    activity: AppCompatActivity, private val tabCount: Int
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}