package com.vgrec.checkersboard

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.PieceRank
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.rules.PlayerGameRules
import com.vgrec.checkersboard.rules.GameRules

data class UiState(
    val board: Array<Array<Square>>,
    val validPositions: List<Position> = emptyList(),
    val prevClickedPosition: Position? = null,
)

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState(board = emptyArray()))
        private set

    private val gameRules: GameRules = PlayerGameRules()
    private val boardManager = BoardManager()
    private var playerShouldMove = true

    init {
        val board = boardManager.buildInitialBoard(
            opponentPieceColor = PieceColor.LIGHT,
            myPieceColor = PieceColor.DARK
        )
        uiState = UiState(board = board)
    }

    fun handleClickAtPosition(position: Position) {
        when {
            canPlace(position = position) -> {
                val updatedBoard: Array<Array<Square>> = gameRules.place(
                    position = position,
                    prevPosition = uiState.prevClickedPosition!!,
                    board = uiState.board
                )
                uiState = uiState.copy(
                    board = updatedBoard,
                    validPositions = emptyList(),
                    prevClickedPosition = null
                )
            }
            canPick(position = position) -> {
                val validPositions: List<Position> = gameRules.findValidPositionsToMoveForPlayer(
                    position = position,
                    board = uiState.board
                )
                uiState = uiState.copy(
                    validPositions = validPositions,
                    prevClickedPosition = position
                )
            }
            else -> {
                uiState = uiState.copy(
                    validPositions = emptyList(),
                    prevClickedPosition = null
                )
            }
        }
    }

    private fun canPick(position: Position): Boolean =
        gameRules.canPick(
            position = position,
            board = uiState.board,
            playerPiece = Piece(  // TODO: read this from somewhere
                color = PieceColor.DARK,
                rank = PieceRank.MAN
            )
        )

    private fun canPlace(position: Position): Boolean =
        uiState.prevClickedPosition != null && uiState.validPositions.contains(position)

    fun calculateSquareWidth(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return (displayMetrics.widthPixels / BOARD_SIZE).toDp
    }
}