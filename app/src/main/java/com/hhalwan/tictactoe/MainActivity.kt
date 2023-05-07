package com.hhalwan.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hhalwan.tictactoe.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.ticTacToeButton.setOnClickListener {
            startActivity(Intent(this, TicTacToeAct::class.java))
        }
        binding.ticTacTakeButton.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
    }
}
