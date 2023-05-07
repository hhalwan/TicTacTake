package com.hhalwan.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

// Define a fragment for the first page of the "How to Play" activity

class Page1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page1, container, false)

        view.findViewById<Button>(R.id.next).setOnClickListener {
            val vp = activity!!.findViewById<ViewPager2>(R.id.view_pager)
            vp.currentItem = vp.currentItem+1
        }

        return view
    }

}