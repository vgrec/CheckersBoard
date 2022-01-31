package com.vgrec.checkersboard.model

data class Piece(
    val color: PieceColor,
    val rank: PieceRank,
)

enum class PieceColor {
    LIGHT,
    DARK,
}

enum class PieceRank {
    MAN,
    KING
}