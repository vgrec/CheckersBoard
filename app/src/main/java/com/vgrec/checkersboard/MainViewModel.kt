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
        buildInitialBoard(
            opponentPieceColor = PieceColor.LIGHT,
            myPieceColor = PieceColor.DARK
        )
    }

    private fun getStartingPositionsForOpponentPieces(): List<Position> {
        return getStartingPositionsForPieces(
            startingRow = 0,
            totalRows = 3,
            piecesPerRow = 4
        )
    }

    private fun getStartingPositionsForMyPieces(): List<Position> {
        return getStartingPositionsForPieces(
            startingRow = 5,
            totalRows = 3,
            piecesPerRow = 4
        )
    }

    private fun getStartingPositionsForPieces(
        startingRow: Int,
        totalRows: Int,
        piecesPerRow: Int,
    ): List<Position> {
        return mutableListOf<Position>().apply {
            (startingRow until startingRow + totalRows).forEach { row ->
                val startingColumn = if (row % 2 == 0) 1 else 0

                (startingColumn until piecesPerRow * 2)
                    .step(2)
                    .forEach { col ->
                        add(
                            Position(
                                rowIndex = row,
                                colIndex = col
                            )
                        )
                    }
            }
        }
    }

    private fun buildInitialBoard(opponentPieceColor: PieceColor, myPieceColor: PieceColor) {
        val opponentStartingPositions: List<Position> = getStartingPositionsForOpponentPieces()
        val myStartingPositions: List<Position> = getStartingPositionsForMyPieces()

        val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Square(color = Color.White) } }
        (0 until BOARD_SIZE).forEach { row ->
            (0 until BOARD_SIZE).forEach { col ->
                val color = if (row % 2 == 0) {
                    if (col % 2 == 0) LIGHT_SQUARE_COLOR else DARK_SQUARE_COLOR
                } else {
                    if (col % 2 != 0) LIGHT_SQUARE_COLOR else DARK_SQUARE_COLOR
                }

                val currentPosition = Position(row, col)
                val piece: Piece? = when {
                    opponentStartingPositions.contains(currentPosition) -> Piece(
                        color = opponentPieceColor,
                        rank = PieceRank.MAN
                    )
                    myStartingPositions.contains(currentPosition) -> Piece(
                        color = myPieceColor,
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