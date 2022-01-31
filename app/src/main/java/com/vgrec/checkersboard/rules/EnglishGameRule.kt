package com.vgrec.checkersboard.rules

import android.util.Log
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.Square

class EnglishGameRule : GameRule {
    override fun canPick(
        position: Pair<Int, Int>,
        board: Array<Array<Square>>,
        myPiece: Piece,
    ): Boolean {
        val clickedSquare: Square = board[position.first][position.second]
        if (myPiece != clickedSquare.piece) {
            Log.d("GREC_T", "Clicked on an empty square or opponents piece, returning.")
            return false
        }

        return true
    }

    override fun canPlace(
        position: Pair<Int, Int>,
        board: Array<Array<Square>>,
        myPiece: Piece,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun pick(position: Pair<Int, Int>, board: Array<Array<Square>>) {
        TODO("Not yet implemented")
    }

    override fun place(position: Pair<Int, Int>, board: Array<Array<Square>>) {
        TODO("Not yet implemented")
    }
}