package com.example.exchangecurrency.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.example.exchangecurrency.R
import com.example.exchangecurrency.ui.view.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.view_pager_layout.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = pagerAdapter
        vpPager.setPageTransformer(true, CubeOutTransformer())
    }
}
