package com.hhalwan.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2

// Define a fragment for the second page of the "How to Play" activity

class Page2Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page2, container, false)

        view.findViewById<Button>(R.id.next2).setOnClickListener {
            val vp = activity!!.findViewById<ViewPager2>(R.id.view_pager)
            vp.currentItem = vp.currentItem+1
        }

        return view
    }
}