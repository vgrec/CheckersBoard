package com.vgrec.checkersboard.rules

import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square

interface GameRules {
    fun place(newPosition: Position, prevPosition: Position, board: Array<Array<Square>>): Array<Array<Square>>
    fun canPick(position: Position, board: Array<Array<Square>>, playerPiece: Piece): Boolean
    fun findValidPositionsToMoveForPlayer(
        playerColor: PieceColor,
        position: Position,
        board: Array<Array<Square>>,
    ): List<Position>
}