package com.example.cair.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cair.fragments.GrafimaFragment
import com.example.cair.fragments.InfoFragment


@Suppress("DEPRECATION")

internal class FragmentAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> { InfoFragment() }
            1 -> { GrafimaFragment() }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}


