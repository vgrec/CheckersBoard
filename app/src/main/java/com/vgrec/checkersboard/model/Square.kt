package com.vgrec.checkersboard.model

import androidx.compose.ui.graphics.Color
import com.vgrec.checkersboard.Piece

data class Square(
    val color: Color,
    val piece: Piece? = null,
)
