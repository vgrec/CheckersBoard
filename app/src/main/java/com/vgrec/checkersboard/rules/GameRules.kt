package com.vgrec.checkersboard.rules

import com.vgrec.checkersboard.model.Board
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.Position

interface GameRules {
    fun place(newPosition: Position, prevPosition: Position, board: Board): Board
    fun canPick(position: Position, board: Board, playerPiece: Piece): Boolean
    fun findValidPositionsToMoveForPlayer(
        playerColor: PieceColor,
        position: Position,
        board: Board,
    ): List<Position>
}