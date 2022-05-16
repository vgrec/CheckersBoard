package com.vgrec.checkersboard.rules

import android.util.Log
import com.vgrec.checkersboard.BOARD_SIZE
import com.vgrec.checkersboard.model.Board
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

class OpponentGameRules : GameRules {
    override fun canPick(
        position: Position,
        board: Board,
        playerPiece: Piece,
    ): Boolean {
        val clickedSquare: Square = board[position]
        val clickedPiece: Piece = clickedSquare.piece ?: return false

        if (isSquareEmpty(clickedSquare)) {
            return false
        }

        if (isNotMyPiece(playerPiece, clickedPiece)) {
            return false
        }

        val validPositions = findValidPositionsToMoveForPlayer(
            playerColor = playerPiece.color,
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

    override fun findValidPositionsToMoveForPlayer(
        playerColor: PieceColor,
        position: Position,
        board: Board,
    ): List<Position> {
        val isMan: Boolean =
            board[position].piece?.rank == PieceRank.MAN

        // TODO: it has bug that a king can jump over its own pieces

        val maxAllowedSteps = if (isMan) 1 else BOARD_SIZE
        val nextRowIndex = position.rowIndex + 1
        val rightColumnIndex = position.colIndex + 1
        val leftColumnIndex = position.colIndex - 1

        return mutableListOf<Position>().apply {
            addAll(
                findValidPositionsOnTheRightDiagonal(
                    board = board,
                    maxAllowedSteps = maxAllowedSteps,
                    nextRowIndex = nextRowIndex,
                    rightColumnIndex = leftColumnIndex
                )
            )

            addAll(
                findValidPositionsOnTheLeftDiagonal(
                    board = board,
                    maxAllowedSteps = maxAllowedSteps,
                    nextRowIndex = nextRowIndex,
                    leftColumnIndex = rightColumnIndex
                )
            )
        }
    }

    private fun findValidPositionsOnTheRightDiagonal(
        board: Board,
        maxAllowedSteps: Int,
        nextRowIndex: Int,
        rightColumnIndex: Int,
    ): List<Position> {
        val validPositions = mutableListOf<Position>()

        var currentStep = 0
        var rowIndex = nextRowIndex
        var colIndex = rightColumnIndex

        while (currentStep < maxAllowedSteps
            && rowIndex < BOARD_SIZE
            && colIndex >= 0
        ) {
            val square: Square = board[Position(rowIndex = rowIndex, colIndex = colIndex)]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                )
            }
            rowIndex++
            colIndex--
            currentStep++
        }

        return validPositions
    }

    private fun findValidPositionsOnTheLeftDiagonal(
        board: Board,
        maxAllowedSteps: Int,
        nextRowIndex: Int,
        leftColumnIndex: Int,
    ): List<Position> {
        val validPositions = mutableListOf<Position>()

        var currentStep = 0
        var rowIndex = nextRowIndex
        var colIndex = leftColumnIndex

        while (currentStep < maxAllowedSteps
            && rowIndex < BOARD_SIZE
            && colIndex < BOARD_SIZE
        ) {
            val square: Square = board.getByIndexes(rowIndex, colIndex)
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                )
            }
            rowIndex++
            colIndex++
            currentStep++
        }

        return validPositions
    }

    override fun place(
        newPosition: Position,
        prevPosition: Position,
        board: Board,
    ): Board {
        val prevSquare = board[prevPosition]
        val newSquareNumber = board[newPosition].number
        board[newPosition] = prevSquare.copy(number = newSquareNumber)
        board[prevPosition] = prevSquare.copy(piece = null)

        return board
    }
}