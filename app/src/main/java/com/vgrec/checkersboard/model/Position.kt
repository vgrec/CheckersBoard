package com.vgrec.checkersboard.model

import com.vgrec.checkersboard.BOARD_SIZE

data class Position(
    val rowIndex: Int,
    val colIndex: Int,
)

fun Position.topLeftPosition(steps: Int = 1) = Position(
    rowIndex = rowIndex - steps,
    colIndex = colIndex - steps
)

fun Position.topRightPosition(steps: Int = 1) = Position(
    rowIndex = rowIndex - steps,
    colIndex = colIndex + steps
)

fun Position.bottomLeftPosition(steps: Int = 1) = Position(
    rowIndex = rowIndex + steps,
    colIndex = colIndex - steps
)

fun Position.bottomRightPosition(steps: Int = 1) = Position(
    rowIndex = rowIndex + steps,
    colIndex = colIndex + steps
)

fun Position.isValid(): Boolean {
    return rowIndex in 0 until BOARD_SIZE && colIndex in 0 until BOARD_SIZE
}
