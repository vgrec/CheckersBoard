package com.vgrec.checkersboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.rules.CommonGameRules
import com.vgrec.checkersboard.rules.GameRules

data class UiState(
    val board: Array<Array<Square>>,
)

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState(board = emptyArray()))
        private set

    private val gameRules: GameRules = CommonGameRules()
    private val boardManager = BoardManager()

    init {
        val board = boardManager.buildInitialBoard(
            opponentPieceColor = PieceColor.LIGHT,
            myPieceColor = PieceColor.DARK
        )
        uiState = UiState(board = board)
    }

    fun handleClickAtPosition(position: Position) {
        val canPick = gameRules.canPick(
            position = position,
            board = uiState.board,
            // TODO: read this from somewhere
            playerPiece = Piece(
                color = PieceColor.DARK,
                rank = PieceRank.MAN
            )
        )
        Log.d("GREC_T", "Can pick: $canPick")
    }
}