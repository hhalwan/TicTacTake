package com.hhalwan.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// Define a fragment for the third page of the "How to Play" activity

class Page3Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page3, container, false)

        view.findViewById<Button>(R.id.next3).setOnClickListener {
            startActivity(Intent(context, TicTacTakeActivity::class.java))
        }

        return view
    }
}