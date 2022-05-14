package com.vgrec.checkersboard.model

data class Position(
    val rowIndex: Int,
    val colIndex: Int,
)

fun Position.topLeftPosition(): Position {
    return Position(
        rowIndex = rowIndex - 1,
        colIndex = colIndex - 1
    )
}

fun Position.topRightPosition(): Position {
    return Position(
        rowIndex = rowIndex - 1,
        colIndex = colIndex + 1
    )
}

fun Position.bottomLeftPosition(): Position {
    return Position(
        rowIndex = rowIndex + 1,
        colIndex = colIndex - 1
    )
}

fun Position.bottomRightPosition(): Position {
    return Position(
        rowIndex = rowIndex + 1,
        colIndex = colIndex + 1
    )
}
