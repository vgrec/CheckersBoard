package com.vgrec.checkersboard.rules

import android.util.Log
import com.vgrec.checkersboard.BOARD_SIZE
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

class CommonGameRules : GameRules {
    override fun canPick(
        position: Position,
        board: Array<Array<Square>>,
        playerPiece: Piece,
    ): Boolean {
        val clickedSquare: Square = board[position.rowIndex][position.colIndex]
        val clickedPiece: Piece = clickedSquare.piece ?: return false

        if (isSquareEmpty(clickedSquare)) {
            return false
        }

        if (isNotMyPiece(playerPiece, clickedPiece)) {
            return false
        }

        val validPositions = findValidPositionsToMoveForPlayer(
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

    private fun findValidPositionsToMoveForPlayer(
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

        var currentStep = 0
        val maxAllowedSteps = if (isMan) 1 else BOARD_SIZE

        var prevRowIndex = position.rowIndex - 1
        var rightColumnIndex = position.colIndex + 1
        var leftColumnIndex = position.colIndex - 1

        while (currentStep < maxAllowedSteps
            && prevRowIndex >= 0
            && rightColumnIndex < BOARD_SIZE
        ) {
            val square: Square = board[prevRowIndex][rightColumnIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = prevRowIndex,
                        colIndex = rightColumnIndex
                    )
                )
            }
            prevRowIndex--
            rightColumnIndex++
            currentStep++
        }

        while (currentStep < maxAllowedSteps
            && prevRowIndex >= 0
            && leftColumnIndex-- >= 0
        ) {
            val square: Square = board[prevRowIndex][leftColumnIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = prevRowIndex,
                        colIndex = leftColumnIndex
                    )
                )
            }
            prevRowIndex--
            leftColumnIndex--
            currentStep++
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