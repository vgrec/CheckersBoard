package com.vgrec.checkersboard.model

data class Node(
    val currentPosition: Position,
    val capturePosition: Position? = null,
    val children: MutableList<Node> = mutableListOf<Node>(),
    val squareNumber: Int? = null,
)

fun Node.traverse(): List<List<Node>> {
    return emptyList()
}