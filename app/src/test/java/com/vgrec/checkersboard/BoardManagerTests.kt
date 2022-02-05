package com.vgrec.checkersboard

import com.vgrec.checkersboard.model.PieceColor
import org.junit.Test

import org.junit.Assert.*

class BoardManagerTests {
    @Test
    fun determinePlayerPiecesColor() {
        val colors = listOf(PieceColor.LIGHT, PieceColor.DARK)

        val myPieceColor = colors.random()
        val opponentPieceColor = colors.first { it != myPieceColor }

        assertTrue(myPieceColor != opponentPieceColor)
    }
}