package com.vgrec.checkersboard.rules

import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

interface GameRules {
    fun place(position: Position, prevPosition: Position, board: Array<Array<Square>>): Array<Array<Square>>
    fun canPick(position: Position, board: Array<Array<Square>>, playerPiece: Piece): Boolean
    fun findValidPositionsToMoveForPlayer(
        position: Position,
        board: Array<Array<Square>>,
    ): List<Position>
}