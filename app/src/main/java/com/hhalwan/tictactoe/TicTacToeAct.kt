package com.hhalwan.tictactoe

// This code defines the main activity for a Tic Tac Toe game.
// The activity loads a layout that contains a grid of buttons
// representing the game board, and sets up a listener for the
// buttons that handles user input and updates the game state.
// The activity also manages the game logic, keeping track of
// the current player, checking for wins, and handling game over
// conditions such as a tie game or a player win.

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hhalwan.tictactoe.databinding.ActivityTicTacToeBinding

const val EMPTY = R.drawable.button_background
const val X = R.drawable.button_x
const val O = R.drawable.button_o

class TicTacToeViewModel : ViewModel() {
    private var numSquaresOccupied = 0
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    private val _eventGameTie = MutableLiveData<Boolean>()
    val eventGameTie: LiveData<Boolean>
        get() = _eventGameTie
    private val _isXsTurn = MutableLiveData<Boolean>()
    val isXsTurn: LiveData<Boolean>
        get() = _isXsTurn
    val squareTopLeft = MutableLiveData<Int>()
    val squareTopMid = MutableLiveData<Int>()
    val squareTopRight = MutableLiveData<Int>()
    val squareMidLeft = MutableLiveData<Int>()
    val squareMid = MutableLiveData<Int>()
    val squareMidRight = MutableLiveData<Int>()
    val squareBtmLeft = MutableLiveData<Int>()
    val squareBtmMid = MutableLiveData<Int>()
    val squareBtmRight = MutableLiveData<Int>()
    var board = arrayOf(
        arrayOf(squareTopLeft, squareTopMid, squareTopRight),
        arrayOf(squareMidLeft, squareMid, squareMidRight),
        arrayOf(squareBtmLeft, squareBtmMid, squareBtmRight)
        )
    init {
        _isXsTurn.value = true
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = EMPTY
            }
        }
    }
    private fun getNextPlayer(): Int {
        if (_isXsTurn.value == true) {
            _isXsTurn.value = false
            return X
        } else {
            _isXsTurn.value = true
            return O
        }
    }
    fun makeMove(pos1: Int, pos2: Int) {
        numSquaresOccupied++
        val symbol = getNextPlayer() // X or O
        board[pos1][pos2].value = symbol
        checkIfGameFinished(pos1, pos2)
    }
    fun checkIfGameFinished(pos1: Int, pos2: Int) {
        if (numSquaresOccupied < 3)
            return
        if (board[pos1][0].value == board[pos1][1].value && board[pos1][0].value == board[pos1][2].value)
            _eventGameFinish.value = true
        else if (board[0][pos2].value == board[1][pos2].value && board[0][pos2].value == board[2][pos2].value)
            _eventGameFinish.value = true
        else if (pos1 == pos2 && board[0][0].value == board[1][1].value && board[0][0].value == board[2][2].value)
            _eventGameFinish.value = true
        else if ( (pos1 == 0 && pos2 == 2 || pos1 == 1 && pos2 == 1 || pos1 == 2 && pos2 == 0)
                  && board[0][2].value == board[1][1].value && board[0][2].value == board[2][0].value   )
            _eventGameFinish.value = true
        else if (numSquaresOccupied == 9)
            _eventGameTie.value = true
    }
    fun onGameRestart() {
        numSquaresOccupied = 0
        _eventGameTie.value = false
        _eventGameFinish.value = false
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = EMPTY
            }
        }
        _isXsTurn.value = true
    }
}

class TicTacToeAct : AppCompatActivity() {
    private lateinit var binding: ActivityTicTacToeBinding
    private lateinit var ticTacToeViewModel: TicTacToeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tic_tac_toe)
        ticTacToeViewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)
        binding.ticTacToeViewModel = ticTacToeViewModel
        ticTacToeViewModel.isXsTurn.observe(this, { newVal ->
            binding.turnText.text = when (newVal) {
                true -> getString(R.string.x_s_turn)
                else -> getString(R.string.o_s_turn)
            }
        })
        for (i in 0..2) {
            for (j in 0..2) {
                ticTacToeViewModel.board[i][j].observe(this, { newInt ->
                    val btn = getButtonFromPos(i, j)
                    btn.setBackgroundResource(newInt)
                    btn.isClickable = (newInt == EMPTY)
                })
            }
        }
        ticTacToeViewModel.eventGameFinish.observe(this, { hasFinished ->
            if(hasFinished) {
                if (ticTacToeViewModel.isXsTurn.value == false) {
                    binding.winnerText.setText(R.string.x_wins)
                    binding.winnerText.setTextColor(ContextCompat.getColor(
                            this, R.color.orange_800))
                } else {
                    binding.winnerText.setText(R.string.o_wins)
                    binding.winnerText.setTextColor(ContextCompat.getColor(
                        this, R.color.blue_700))
                }
                gameFinished()
            }
        })
        ticTacToeViewModel.eventGameTie.observe(this, { hasFinished ->
            if(hasFinished) {
                binding.winnerText.setText(R.string.tie)
                binding.winnerText.setTextColor(Color.BLACK)
                gameFinished()
            }
        })
        binding.btnTopLeft.setOnClickListener   { ticTacToeViewModel.makeMove(0, 0) }
        binding.btnTopMid.setOnClickListener    { ticTacToeViewModel.makeMove(0, 1) }
        binding.btnTopRight.setOnClickListener  { ticTacToeViewModel.makeMove(0, 2) }
        binding.btnMidLeft.setOnClickListener   { ticTacToeViewModel.makeMove(1, 0) }
        binding.btnMid.setOnClickListener       { ticTacToeViewModel.makeMove(1, 1) }
        binding.btnMidRight.setOnClickListener  { ticTacToeViewModel.makeMove(1, 2) }
        binding.btnBtmLeft.setOnClickListener   { ticTacToeViewModel.makeMove(2, 0) }
        binding.btnBtmMid.setOnClickListener    { ticTacToeViewModel.makeMove(2, 1) }
        binding.btnBtmRight.setOnClickListener  { ticTacToeViewModel.makeMove(2, 2) }

        binding.restartButton.setOnClickListener {
            binding.restartButton.visibility = View.GONE
            binding.turnText.visibility = View.VISIBLE
            binding.winnerText.setText("")
            ticTacToeViewModel.onGameRestart()
        }

    }

    private fun gameFinished() {
        for (i in 0..2) {
            for (j in 0..2) {
                getButtonFromPos(i, j).isClickable = false
            }
        }
        binding.restartButton.visibility = View.VISIBLE
        binding.turnText.visibility = View.GONE
    }

    fun getButtonFromPos(pos1: Int, pos2: Int): ImageButton {
        return when {
            pos1 == 0 && pos2 == 0 -> binding.btnTopLeft
            pos1 == 0 && pos2 == 1 -> binding.btnTopMid
            pos1 == 0 && pos2 == 2 -> binding.btnTopRight
            pos1 == 1 && pos2 == 0 -> binding.btnMidLeft
            pos1 == 1 && pos2 == 1 -> binding.btnMid
            pos1 == 1 && pos2 == 2 -> binding.btnMidRight
            pos1 == 2 && pos2 == 0 -> binding.btnBtmLeft
            pos1 == 2 && pos2 == 1 -> binding.btnBtmMid
            else -> binding.btnBtmRight
        }
    }

}