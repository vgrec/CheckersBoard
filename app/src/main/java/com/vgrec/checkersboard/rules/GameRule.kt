package com.vgrec.checkersboard.rules

interface GameRule {
    fun pick(position: Pair<Int, Int>, board: Array<Array<Square>>)
    fun place(position: Pair<Int, Int>, board: Array<Array<Square>>)
    fun canPick(position: Pair<Int, Int>, board: Array<Array<Square>>, myPiece: Piece): Boolean
    fun canPlace(position: Pair<Int, Int>, board: Array<Array<Square>>, myPiece: Piece): Boolean
}