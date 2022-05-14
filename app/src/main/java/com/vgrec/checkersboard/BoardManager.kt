package com.vgrec.checkersboard

import androidx.compose.ui.graphics.Color
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

class BoardManager {

    fun buildInitialBoard(
        opponentPieceColor: PieceColor,
        myPieceColor: PieceColor,
    ): Array<Array<Square>> {
        val opponentStartingPositions: List<Position> = getStartingPositionsForOpponentPieces()
        val myStartingPositions: List<Position> = getStartingPositionsForPlayerPieces()

        // http://www.bobnewell.net/nucleus/checkers.php?itemid=289
        var squareNumber = 1

        val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Square(color = Color.White) } }
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
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
                    piece = piece,
                    number = if (color == DARK_SQUARE_COLOR) {
                        squareNumber++
                    } else {
                        null
                    }
                )
            }
        }

        return board
    }

    private fun getStartingPositionsForOpponentPieces(): List<Position> {
        return getStartingPositionsForPieces(
            startingRow = 0,
            totalRows = 3,
            piecesPerRow = 4
        )
    }

    private fun getStartingPositionsForPlayerPieces(): List<Position> {
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
            val endingRow = startingRow + totalRows
            for (row in startingRow until endingRow) {
                val startingColumn = if (row % 2 == 0) 1 else 0
                val endingColumn = piecesPerRow * 2
                for (col in startingColumn until endingColumn step 2) {
                    add(Position(
                        rowIndex = row,
                        colIndex = col
                    ))
                }
            }
        }
    }

}