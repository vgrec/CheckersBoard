package com.vgrec.checkersboard.model

import androidx.compose.ui.graphics.Color

data class Square(
    val color: Color,
    val piece: Piece? = null,
)

fun Square.isNotEmpty() = piece != null
