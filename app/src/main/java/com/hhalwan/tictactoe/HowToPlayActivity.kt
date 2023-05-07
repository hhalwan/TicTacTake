package com.hhalwan.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class HowToPlayActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play)

        viewPager = findViewById(R.id.view_pager)
        val fragments: ArrayList<Fragment> = arrayListOf(
            Page1Fragment(),
            Page2Fragment(),
            Page3Fragment()
        )
        val adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        // If we are on the first page, let the system handle the back button
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        // Otherwise, we go to the previous page
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

}