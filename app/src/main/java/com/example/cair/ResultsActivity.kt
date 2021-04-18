package com.example.cair

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.cair.adapters.FragmentAdapter
import com.google.android.material.tabs.TabLayout


class ResultsActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("AIR QUALITY"))
        tabLayout.addTab(tabLayout.newTab().setText("WEATHER"))
        tabLayout.addTab(tabLayout.newTab().setText("FORECAST"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = FragmentAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

     fun getMyData(): Pair<String?, String?> {
         val location =  intent.getStringExtra("EXTRA_LOCATION")
         val isChecked =  intent.getStringExtra("EXTRA_BOOLEAN")
         return Pair(location, isChecked)
     }
}


