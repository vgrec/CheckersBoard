package com.vgrec.checkersboard.rules

import android.util.Log
import com.vgrec.checkersboard.COLS
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

class EnglishGameRules : GameRules {
    override fun canPick(
        position: Position,
        board: Array<Array<Square>>,
        myPiece: Piece,
    ): Boolean {
        val clickedSquare: Square = board[position.rowIndex][position.colIndex]
        val clickedPiece: Piece = clickedSquare.piece ?: return false

        if (isSquareEmpty(clickedSquare)) {
            return false
        }

        if (isNotMyPiece(myPiece, clickedPiece)) {
            return false
        }

        val validPositions = findValidPositionsToMoveFor(
            position = position,
            board = board
        )

        // if no valid positions found then the piece cannot be picked, return.
        if (validPositions.isEmpty()) {
            return false
        } else {
            Log.d("GREC_T", "Can pick because can move at: $validPositions")
        }

        return true
    }

    private fun isSquareEmpty(square: Square): Boolean = square.piece == null

    private fun isNotMyPiece(myPiece: Piece, clickedPiece: Piece): Boolean =
        myPiece.color != clickedPiece.color

    private fun findValidPositionsToMoveFor(
        position: Position,
        board: Array<Array<Square>>,
    ): List<Position> {
        val isMan: Boolean =
            board[position.rowIndex][position.colIndex].piece?.rank == PieceRank.MAN

        // TODO optimization:
        // valid positions can be determined by finding an empty spot when
        // traversing the left and right diagonals.
        // for man: max 1 step
        // for king: traverse the whole diagonal

        val validPositions = mutableListOf<Position>()

        val prevRowIndex = position.rowIndex - 1
        val columnOnTheLeftIndex = position.colIndex - 1
        val columnOnTheRightIndex = position.colIndex + 1
        if (prevRowIndex >= 0 && columnOnTheLeftIndex >= 0) {
            val square: Square = board[prevRowIndex][columnOnTheLeftIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = prevRowIndex,
                        colIndex = columnOnTheLeftIndex
                    )
                )
            }
        }

        if (prevRowIndex >= 0 && columnOnTheRightIndex < COLS) {
            val square: Square = board[prevRowIndex][columnOnTheRightIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = prevRowIndex,
                        colIndex = columnOnTheRightIndex
                    )
                )
            }
        }

        return validPositions
    }


    override fun canPlace(
        position: Position,
        board: Array<Array<Square>>,
        myPiece: Piece,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun pick(position: Position, board: Array<Array<Square>>) {
        TODO("Not yet implemented")
    }

    override fun place(position: Position, board: Array<Array<Square>>) {
        TODO("Not yet implemented")
    }
}