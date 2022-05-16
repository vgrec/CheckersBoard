package com.vgrec.checkersboard.model

class Board(
    private val board: Array<Array<Square>> = emptyArray(),
) {
    operator fun get(position: Position): Square =
        board[position.rowIndex][position.colIndex]

    operator fun set(position: Position, value: Square) {
        board[position.rowIndex][position.colIndex] = value
    }

    fun getByIndexes(rowIndex: Int, colIndex: Int): Square =
        board[rowIndex][colIndex]
}
