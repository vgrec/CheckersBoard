package com.vgrec.checkersboard.rules

import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

interface GameRules {
    fun pick(position: Position, board: Array<Array<Square>>)
    fun place(position: Position, board: Array<Array<Square>>)
    fun canPick(position: Position, board: Array<Array<Square>>, playerPiece: Piece): Boolean
    fun canPlace(position: Position, board: Array<Array<Square>>, myPiece: Piece): Boolean
}