package com.vgrec.checkersboard.rules

import android.util.Log
import com.vgrec.checkersboard.BOARD_SIZE
import com.vgrec.checkersboard.model.Node
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.model.isNotEmpty
import com.vgrec.checkersboard.model.isValid
import com.vgrec.checkersboard.model.topLeftPosition
import com.vgrec.checkersboard.model.topRightPosition
import java.lang.StringBuilder

class PlayerGameRules : GameRules {
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
        board: Array<Array<Square>>,
    ): List<Position> {
        val isMan: Boolean =
            board[position.rowIndex][position.colIndex].piece?.rank == PieceRank.MAN

        // TODO: it has bug that a king can jump over its own pieces

        val maxAllowedSteps = if (isMan) 1 else BOARD_SIZE
        val nextRowIndex = position.rowIndex - 1
        val rightColumnIndex = position.colIndex + 1
        val leftColumnIndex = position.colIndex - 1

        val square = board[position.rowIndex][position.colIndex]
        val root = Node(
            currentPosition = position,
            squareNumber = square.number
        )

        findCaptureMoves(
            playerPiece = playerColor,
            board = board,
            currentNode = root
        )

        printTree(root = root, mutableListOf())

        return mutableListOf<Position>().apply {
            addAll(
                findValidPositionsOnTheRightDiagonal(
                    board = board,
                    maxAllowedSteps = maxAllowedSteps,
                    nextRowIndex = nextRowIndex,
                    rightColumnIndex = rightColumnIndex
                )
            )

            addAll(
                findValidPositionsOnTheLeftDiagonal(
                    board = board,
                    maxAllowedSteps = maxAllowedSteps,
                    nextRowIndex = nextRowIndex,
                    leftColumnIndex = leftColumnIndex
                )
            )
        }
    }

    private fun printTree(root: Node, list: MutableList<Node>) {
        list.add(root)

        if (root.children.isEmpty()) {
            val sb = StringBuilder()
            list.forEach {
                sb.append(it.squareNumber)
                sb.append(" -> ")
            }
            Log.d("GREC_T", "Path: $sb")
        } else {
            root.children.forEach {
                printTree(it, list)
                list.removeLast()
            }
        }
    }

    private fun findCaptureMoves(
        playerPiece: PieceColor,
        board: Array<Array<Square>>,
        currentNode: Node,
    ) {
        val current: Position = currentNode.currentPosition

        val topLeftPosition = current.topLeftPosition()
        val topLeftJumpPosition = current.topLeftPosition(2)
        if (canCapture(
                playerColor = playerPiece,
                board = board,
                nextPosition = topLeftPosition,
                jumpPosition = topLeftJumpPosition)
        ) {
            val square = board[topLeftJumpPosition.rowIndex][topLeftJumpPosition.colIndex]

            val node = Node(
                currentPosition = topLeftJumpPosition,
                capturePosition = topLeftPosition,
                squareNumber = square.number
            )

            currentNode.children.add(node)

            findCaptureMoves(
                playerPiece = playerPiece,
                board = board,
                currentNode = node
            )
        }

        val topRightPosition = current.topRightPosition()
        val topRightJumpPosition = current.topRightPosition(2)
        if (canCapture(
                playerColor = playerPiece,
                board = board,
                nextPosition = topRightPosition,
                jumpPosition = topRightJumpPosition)
        ) {
            val square = board[topRightJumpPosition.rowIndex][topRightJumpPosition.colIndex]

            val node = Node(
                currentPosition = topRightJumpPosition,
                capturePosition = topRightPosition,
                squareNumber = square.number
            )

            currentNode.children.add(node)

            findCaptureMoves(
                playerPiece = playerPiece,
                board = board,
                currentNode = node
            )
        }

    }

    private fun canCapture(
        playerColor: PieceColor,
        board: Array<Array<Square>>,
        nextPosition: Position,
        jumpPosition: Position,
    ): Boolean {

        if (nextPosition.isValid() && jumpPosition.isValid()) {
            val nextSquare: Square =
                board[nextPosition.rowIndex][nextPosition.colIndex]
            val jumpSquare: Square =
                board[jumpPosition.rowIndex][jumpPosition.colIndex]

            return nextSquare.isNotEmpty()
                    && nextSquare.piece!!.color != playerColor
                    && jumpSquare.piece == null
        }

        return false
    }

    private fun findValidPositionsOnTheRightDiagonal(
        board: Array<Array<Square>>,
        maxAllowedSteps: Int,
        nextRowIndex: Int,
        rightColumnIndex: Int,
    ): List<Position> {
        val validPositions = mutableListOf<Position>()

        var currentStep = 0
        var rowIndex = nextRowIndex
        var colIndex = rightColumnIndex

        while (currentStep < maxAllowedSteps
            && rowIndex >= 0
            && colIndex < BOARD_SIZE
        ) {
            val square: Square = board[rowIndex][colIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                )
            }
            rowIndex--
            colIndex++
            currentStep++
        }

        return validPositions
    }

    private fun findValidPositionsOnTheLeftDiagonal(
        board: Array<Array<Square>>,
        maxAllowedSteps: Int,
        nextRowIndex: Int,
        leftColumnIndex: Int,
    ): List<Position> {
        val validPositions = mutableListOf<Position>()

        var currentStep = 0
        var rowIndex = nextRowIndex
        var colIndex = leftColumnIndex

        while (currentStep < maxAllowedSteps
            && rowIndex >= 0
            && colIndex >= 0
        ) {
            val square: Square = board[rowIndex][colIndex]
            if (isSquareEmpty(square = square)) {
                validPositions.add(
                    Position(
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                )
            }
            rowIndex--
            colIndex--
            currentStep++
        }

        return validPositions
    }

    override fun place(
        newPosition: Position,
        prevPosition: Position,
        board: Array<Array<Square>>,
    ): Array<Array<Square>> {
        val prevSquare = board[prevPosition.rowIndex][prevPosition.colIndex]
        val newSquareNumber = board[newPosition.rowIndex][newPosition.colIndex].number
        board[newPosition.rowIndex][newPosition.colIndex] =
            prevSquare.copy(number = newSquareNumber)
        board[prevPosition.rowIndex][prevPosition.colIndex] = prevSquare.copy(piece = null)

        return board
    }
}