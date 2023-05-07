package com.hhalwan.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hhalwan.tictactoe.databinding.ActivityTicTacTakeBinding
import kotlin.Int.Companion.MAX_VALUE

// This code defines the main activity for a "Tic Tac Take" game.
// The activity loads a layout that contains a grid of buttons
// representing the game board, and sets up a listener for the
// buttons that handles user input and updates the game state.
// The activity also manages the game logic, keeping track of
// the current player, checking for wins, and handling game over
// conditions such as a tie game or a player win.

const val EMPTY_VAL = 0
const val X1 = 1
const val X2 = 2
const val X3 = 3
const val X4 = 4
const val X5 = 5
const val X6 = 6
const val O1 = 7
const val O2 = 8
const val O3 = 9
const val O4 = 10
const val O5 = 11
const val O6 = 12

class TicTacTakeViewModel : ViewModel() {
    private var numSquaresOccupied = 0
    private var numXPiecesLeft = 6
    private var numOPiecesLeft = 6
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    private val _eventGameTie = MutableLiveData<Boolean>()
    val eventGameTie: LiveData<Boolean>
        get() = _eventGameTie
    private val _isXsTurn = MutableLiveData<Boolean>()
    val isXsTurn: LiveData<Boolean>
        get() = _isXsTurn
    val oPiece1 = MutableLiveData<Int>()
    val oPiece2 = MutableLiveData<Int>()
    val oPiece3 = MutableLiveData<Int>()
    val oPiece4 = MutableLiveData<Int>()
    val oPiece5 = MutableLiveData<Int>()
    val oPiece6 = MutableLiveData<Int>()
    val xPiece1 = MutableLiveData<Int>()
    val xPiece2 = MutableLiveData<Int>()
    val xPiece3 = MutableLiveData<Int>()
    val xPiece4 = MutableLiveData<Int>()
    val xPiece5 = MutableLiveData<Int>()
    val xPiece6 = MutableLiveData<Int>()
    var pieces = arrayOf(arrayOf(xPiece1, xPiece2, xPiece3, xPiece4, xPiece5, xPiece6),
        arrayOf(oPiece1, oPiece2, oPiece3, oPiece4, oPiece5, oPiece6))
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

    var mostRecentPiece = EMPTY_VAL
    var smallestPieceOnBoardSize = MAX_VALUE

    init {
        _isXsTurn.value = true
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = EMPTY_VAL
            }
        }

        pieces[0][0].value = X1
        pieces[0][1].value = X2
        pieces[0][2].value = X3
        pieces[0][3].value = X4
        pieces[0][4].value = X5
        pieces[0][5].value = X6
        pieces[1][0].value = O1
        pieces[1][1].value = O2
        pieces[1][2].value = O3
        pieces[1][3].value = O4
        pieces[1][4].value = O5
        pieces[1][5].value = O6

    }

    fun makeMove(pos1: Int, pos2: Int): Boolean {
        val mostRecentPieceSize = sizePoints(mostRecentPiece)
        if (mostRecentPieceSize > sizePoints(board[pos1][pos2].value)) {
            if (mostRecentPieceSize < smallestPieceOnBoardSize) smallestPieceOnBoardSize = mostRecentPieceSize
            if (board[pos1][pos2].value == EMPTY_VAL) numSquaresOccupied++
            _isXsTurn.value = !_isXsTurn.value!!
            board[pos1][pos2].value = mostRecentPiece
            if (checkIfGameFinished(pos1, pos2) == true) return false
            return true
        } else {
            return false
        }
    }

    private fun sizePoints(piece: Int?): Int {
        return when (piece) {
            O1 -> 1
            O2 -> 2
            O3 -> 3
            O4 -> 4
            O5 -> 5
            O6 -> 6
            X1 -> 1
            X2 -> 2
            X3 -> 3
            X4 -> 4
            X5 -> 5
            X6 -> 6
            else -> 0
        }
    }

    fun checkIfGameFinished(pos1: Int, pos2: Int): Boolean {
        if (numSquaresOccupied < 3)
            return false
        if (areSameColor(board[pos1][0].value, board[pos1][1].value) && areSameColor(board[pos1][0].value, board[pos1][2].value)) {
            _eventGameFinish.value = true
            return true
        }
        if (areSameColor(board[0][pos2].value, board[1][pos2].value) && areSameColor(board[0][pos2].value, board[2][pos2].value)) {
            _eventGameFinish.value = true
            return true
        }
        if (pos1 == pos2 && areSameColor(board[0][0].value, board[1][1].value) && areSameColor(board[0][0].value, board[2][2].value)) {
            _eventGameFinish.value = true
            return true
        }
        if ( (pos1 == 0 && pos2 == 2 || pos1 == 1 && pos2 == 1 || pos1 == 2 && pos2 == 0)
            && areSameColor(board[0][2].value, board[1][1].value) && areSameColor(board[0][2].value, board[2][0].value)   ) {
            _eventGameFinish.value = true
            return true
        }
        if (numSquaresOccupied == 9) {
            val biggestPieceOfCurrPlayer = biggestPieceOfCurrPlayer()
            if (sizePoints(biggestPieceOfCurrPlayer) <= smallestPieceOnBoardSize) {
                _eventGameTie.value = true
                return true
            }
        }
        if (numXPiecesLeft+numOPiecesLeft == 0) {
            _eventGameTie.value = true
            return true
        }
        return false
    }

    private fun biggestPieceOfCurrPlayer(): Int? {
        val currPlayer = if (_isXsTurn.value == true) { 0 } else { 1 }
        for (i in 5 downTo 0) {
            val currPiece = pieces[currPlayer][i].value
            if (currPiece != EMPTY_VAL) return currPiece
        }
        return EMPTY_VAL
    }

    private fun areSameColor(piece1: Int?, piece2: Int?): Boolean {
        if (piece1 == EMPTY_VAL || piece2 == EMPTY_VAL) {
            return piece1 == piece2
        }
        val piece1IsO = (piece1 == O1 || piece1 == O2 || piece1 == O3 ||
                piece1 == O4 || piece1 == O5 || piece1 == O6)
        val piece2IsO = (piece2 == O1 || piece2 == O2 || piece2 == O3 ||
                piece2 == O4 || piece2 == O5 || piece2 == O6)

        return piece1IsO == piece2IsO
    }

    fun onGameRestart() {
        numSquaresOccupied = 0
        numXPiecesLeft = 6
        numOPiecesLeft = 6
        smallestPieceOnBoardSize = MAX_VALUE
        _eventGameTie.value = false
        _eventGameFinish.value = false
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = EMPTY_VAL
            }
        }
        pieces[0][0].value = X1
        pieces[0][1].value = X2
        pieces[0][2].value = X3
        pieces[0][3].value = X4
        pieces[0][4].value = X5
        pieces[0][5].value = X6
        pieces[1][0].value = O1
        pieces[1][1].value = O2
        pieces[1][2].value = O3
        pieces[1][3].value = O4
        pieces[1][4].value = O5
        pieces[1][5].value = O6
        _isXsTurn.value = true
    }

    fun pieceSelected(player: Int, pieceNum: Int): Boolean {
        if (pieces[player][pieceNum].value == EMPTY_VAL) return false
        mostRecentPiece = pieces[player][pieceNum].value!!
        pieces[player][pieceNum].value = EMPTY_VAL
        if (player == 0) numXPiecesLeft--
        else numOPiecesLeft--
        return true
    }

}

class TicTacTakeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicTacTakeBinding
    private lateinit var ticTacTakeViewModel: TicTacTakeViewModel
    lateinit var pieces: Array<Array<ImageButton>>
    var usingSmallLayout = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tic_tac_take)
        ticTacTakeViewModel = ViewModelProvider(this).get(TicTacTakeViewModel::class.java)
        binding.ticTacTakeViewModel = ticTacTakeViewModel
        pieces = arrayOf(arrayOf(binding.xPiece1, binding.xPiece2, binding.xPiece3, binding.xPiece4, binding.xPiece5, binding.xPiece6),
            arrayOf(binding.oPiece1, binding.oPiece2, binding.oPiece3, binding.oPiece4, binding.oPiece5, binding.oPiece6))
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels / displayMetrics.density
        val height = displayMetrics.heightPixels / displayMetrics.density
        val smallestScreenWidth = if (width < height) { width } else { height }
        usingSmallLayout = smallestScreenWidth < 372
        ticTacTakeViewModel.isXsTurn.observe(this, { newVal ->
            binding.turnText.text = when (newVal) {
                true -> getString(R.string.oranges_turn)
                else -> getString(R.string.blues_turn)
            }
        })
        for (i in 0..2) {
            for (j in 0..2) {

                val btn = getButtonFromPos(i, j)

                ticTacTakeViewModel.board[i][j].observe(this, { newInt ->
                    btn.setBackgroundResource(getDrawableOf(newInt))
                })

                btn.setOnClickListener {
                    // returns false if game has finished or move not successfully made
                    if (ticTacTakeViewModel.makeMove(i, j) == true) {
                        changeBoardClickability(false)
                        if (ticTacTakeViewModel.isXsTurn.value == true) changeXPiecesClickability(true)
                        else changeOPiecesClickability(true)
                    }
                }

            }
        } //Note: It's possible these should be in the xml instead
        for (i in 0..1) {
            for (j in 0..5) {
                ticTacTakeViewModel.pieces[i][j].observe(this, { newInt ->
                    pieces[i][j].setBackgroundResource(getDrawableOf(newInt))
                })

                pieces[i][j].setOnClickListener {
                    if (ticTacTakeViewModel.pieceSelected(i, j) == true) {
                        changeBoardClickability(true)
                        if (i == 0) {
                            binding.turnText.text = getString(R.string.oranges_turn_2)
                            changeXPiecesClickability(false)
                        } else {
                            binding.turnText.text = getString(R.string.blues_turn_2)
                            changeOPiecesClickability(false)
                        }
                    }
                }
            }
        }
        ticTacTakeViewModel.eventGameFinish.observe(this, { hasFinished ->
            if(hasFinished) {
                if (ticTacTakeViewModel.isXsTurn.value == false) {
                    binding.winnerText.setText(R.string.orange_wins)
                    binding.winnerText.setTextColor(ContextCompat.getColor(
                        this, R.color.orange_800))
                } else {
                    binding.winnerText.setText(R.string.blue_wins)
                    binding.winnerText.setTextColor(ContextCompat.getColor(
                        this, R.color.blue_700))
                }
                gameFinished()
            }
        })
        ticTacTakeViewModel.eventGameTie.observe(this, { hasFinished ->
            if(hasFinished) {
                binding.winnerText.setText(R.string.tie)
                binding.winnerText.setTextColor(Color.BLACK)
                gameFinished()
            }
        })

        binding.restartButton.setOnClickListener {
            binding.restartButton.visibility = View.GONE
            binding.turnText.visibility = View.VISIBLE
            binding.winnerText.setText("")
            ticTacTakeViewModel.onGameRestart()
            changeXPiecesClickability(true)
        }

        changeBoardClickability(false)
        changeOPiecesClickability(false)
    }

    private fun changeXPiecesClickability(bool: Boolean) {
        for (j in 0..5) {
            pieces[0][j].isClickable = bool
        }
    }
    private fun changeOPiecesClickability(bool: Boolean) {
        for (j in 0..5) {
            pieces[1][j].isClickable = bool
        }
    }

    private fun changeBoardClickability(bool: Boolean) {
        for (i in 0..2) {
            for (j in 0..2) {
                getButtonFromPos(i, j).isClickable = bool
            }
        }
    }

    private fun gameFinished() {
        changeBoardClickability(false)
        changeXPiecesClickability(false)
        changeOPiecesClickability(false)
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

    private fun getDrawableOf(piece: Int?): Int {
        if (usingSmallLayout) {

            return when (piece) {
                O1 -> R.drawable.button_o_size1_smaller
                O2 -> R.drawable.button_o_size2_smaller
                O3 -> R.drawable.button_o_size3_smaller
                O4 -> R.drawable.button_o_size4_smaller
                O5 -> R.drawable.button_o_size5_smaller
                O6 -> R.drawable.button_o_size6_smaller
                X1 -> R.drawable.button_x_size1_smaller
                X2 -> R.drawable.button_x_size2_smaller
                X3 -> R.drawable.button_x_size3_smaller
                X4 -> R.drawable.button_x_size4_smaller
                X5 -> R.drawable.button_x_size5_smaller
                X6 -> R.drawable.button_x_size6_smaller
                else -> R.drawable.button_background
            }

        }

        return when (piece) {
            O1 -> R.drawable.button_o_size1
            O2 -> R.drawable.button_o_size2
            O3 -> R.drawable.button_o_size3
            O4 -> R.drawable.button_o_size4
            O5 -> R.drawable.button_o_size5
            O6 -> R.drawable.button_o_size6
            X1 -> R.drawable.button_x_size1
            X2 -> R.drawable.button_x_size2
            X3 -> R.drawable.button_x_size3
            X4 -> R.drawable.button_x_size4
            X5 -> R.drawable.button_x_size5
            X6 -> R.drawable.button_x_size6
            else -> R.drawable.button_background
        }
    }

}