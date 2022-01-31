package com.vgrec.checkersboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.rules.EnglishGameRule
import com.vgrec.checkersboard.rules.GameRule

data class UiState(
    val board: Array<Array<Square>>,
)

const val ROWS = 8
const val COLS = 8

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState(board = emptyArray()))
        private set

    private val gameRule: GameRule = EnglishGameRule()

    init {
        initBoard()
    }

    private fun initBoard() {
        val darkSquareColor = Color.Green
        val lightSquareColor = Color.White

        val initialLightPiecePositions = listOf(
            Pair(0, 1), Pair(0, 3), Pair(0, 5), Pair(0, 7),
            Pair(1, 0), Pair(1, 2), Pair(1, 4), Pair(1, 6),
            Pair(2, 1), Pair(2, 3), Pair(2, 5), Pair(2, 7)
        )

        val initialDarkPiecePositions = listOf(
            Pair(5, 0), Pair(5, 2), Pair(5, 4), Pair(5, 6),
            Pair(6, 1), Pair(6, 3), Pair(6, 5), Pair(6, 7),
            Pair(7, 0), Pair(7, 2), Pair(7, 4), Pair(7, 6),
        )

        val board = Array(ROWS) { Array(COLS) { Square(color = Color.White) } }
        (0 until ROWS).forEach { row ->
            (0 until COLS).forEach { col ->
                val color = if (row % 2 == 0) {
                    if (col % 2 == 0) lightSquareColor else darkSquareColor
                } else {
                    if (col % 2 != 0) lightSquareColor else darkSquareColor
                }

                val currentPosition = Pair(row, col)
                val piece: Piece? = when {
                    initialLightPiecePositions.contains(currentPosition) -> Piece.LIGHT
                    initialDarkPiecePositions.contains(currentPosition) -> Piece.DARK
                    else -> null // empty square
                }

                board[row][col] = Square(
                    color = color,
                    piece = piece
                )
            }
        }

        uiState = UiState(board = board)
    }

    fun handleClickAtPosition(rowIndex: Int, colIndex: Int) {
        val canPick =
            gameRule.canPick(Pair(rowIndex, colIndex), uiState.board, Piece.DARK)

        Log.d("GREC_T", "Can pick: $canPick")
    }
}