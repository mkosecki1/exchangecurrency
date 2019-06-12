package com.example.exchangecurrency.ui.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.exchangecurrency.ui.view.fragments.GoldFragment
import com.example.exchangecurrency.ui.view.fragments.HomeFragment

class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        private const val MAX_VALUE = 2
    }

    override fun getCount(): Int {
        return MAX_VALUE
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> HomeFragment.newInstance(0, "Kursy Walut")
            1 -> GoldFragment.newInstance(1, "Cena złota")
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "Kursy Walut" else "Cena złota"
    }
}