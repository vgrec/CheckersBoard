package com.vgrec.checkersboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.rules.CommonGameRules
import com.vgrec.checkersboard.rules.GameRules

data class UiState(
    val board: Array<Array<Square>>,
)

const val BOARD_SIZE = 8

private val LIGHT_SQUARE_COLOR = Color.White
private val DARK_SQUARE_COLOR = Color.Green

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState(board = emptyArray()))
        private set

    private val gameRules: GameRules = CommonGameRules()

    init {
        buildInitialBoard()
    }

    private fun buildInitialBoard() {
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

        val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Square(color = Color.White) } }
        (0 until BOARD_SIZE).forEach { row ->
            (0 until BOARD_SIZE).forEach { col ->
                val color = if (row % 2 == 0) {
                    if (col % 2 == 0) LIGHT_SQUARE_COLOR else DARK_SQUARE_COLOR
                } else {
                    if (col % 2 != 0) LIGHT_SQUARE_COLOR else DARK_SQUARE_COLOR
                }

                val currentPosition = Pair(row, col)
                val piece: Piece? = when {
                    initialLightPiecePositions.contains(currentPosition) -> Piece(
                        color = PieceColor.LIGHT,
                        rank = PieceRank.MAN
                    )
                    initialDarkPiecePositions.contains(currentPosition) -> Piece(
                        color = PieceColor.DARK,
                        rank = PieceRank.MAN
                    )
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

    fun handleClickAtPosition(position: Position) {
        val canPick = gameRules.canPick(
            position = position,
            board = uiState.board,
            // TODO: read this from somewhere
            myPiece = Piece(
                color = PieceColor.DARK,
                rank = PieceRank.MAN
            )
        )
        Log.d("GREC_T", "Can pick: $canPick")
    }
}