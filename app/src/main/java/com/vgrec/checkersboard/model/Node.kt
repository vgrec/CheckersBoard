package com.vgrec.checkersboard.model

data class Node(
    val currentPosition: Position,
    val capturePosition: Position,
    val children: List<Node> = mutableListOf<Node>(),
)

fun Node.traverse(): List<List<Node>> {
    return emptyList()
}