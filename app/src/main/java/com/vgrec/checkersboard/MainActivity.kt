package com.vgrec.checkersboard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vgrec.checkersboard.ui.theme.CheckersBoardTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckersBoardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CheckersBoards("Android")
                }
            }
        }
    }
}

enum class Action {
    READY_TO_PICK,
    READY_TO_PLACE
}

enum class Piece {
    WHITE,
    BLACK,
}

data class Square(
    val color: Color = Color.Red,
    val piece: Piece? = null,
)

@ExperimentalFoundationApi
@Composable
fun CheckersBoards(name: String) {
    val blackSquareColor = Color.Green
    val whiteSquareColor = Color.White
    val rows = 8
    val cols = 8

    val initialWhitePiecePositions = listOf(
        Pair(0, 1), Pair(0, 3), Pair(0, 5), Pair(0, 7),
        Pair(1, 0), Pair(1, 2), Pair(1, 4), Pair(1, 6),
        Pair(2, 1), Pair(2, 3), Pair(2, 5), Pair(2, 7)
    )

    val initialBlackPiecePositions = listOf(
        Pair(5, 0), Pair(5, 2), Pair(5, 4), Pair(5, 6),
        Pair(6, 1), Pair(6, 3), Pair(6, 5), Pair(6, 7),
        Pair(7, 0), Pair(7, 2), Pair(7, 4), Pair(7, 6),
    )

    val board = Array(rows) { Array(cols) { Square() } }
    (0 until rows).forEach { row ->
        (0 until cols).forEach { col ->
            val color = if (row % 2 == 0) {
                if (col % 2 == 0) whiteSquareColor else blackSquareColor
            } else {
                if (col % 2 != 0) whiteSquareColor else blackSquareColor
            }

            val currentPosition = Pair(row, col)
            val piece: Piece? = when {
                initialWhitePiecePositions.contains(currentPosition) -> Piece.WHITE
                initialBlackPiecePositions.contains(currentPosition) -> Piece.BLACK
                else -> null
            }

            board[row][col] = Square(
                color = color,
                piece = piece
            )
        }
    }


    val items: List<String> = (0 until rows).map { "$it" }
    LazyColumn {
        itemsIndexed(items) { rowIndex: Int, item: String ->
            Row {
                (0 until cols).map { colIndex: Int ->
                    val square: Square = board[rowIndex][colIndex]

                    val symbol: String = square.piece?.let { piece ->
                        if (piece == Piece.WHITE) {
                            "w"
                        } else {
                            "b"
                        }
                    } ?: ""

                    Text(
                        textAlign = TextAlign.Center,
                        text = AnnotatedString(symbol),
                        modifier = Modifier
                            .background(color = square.color)
                            .width(40.dp)
                            .height(40.dp)
                            .clickable {
                                Log.d("GREC_T", "Clicked: [$rowIndex:$colIndex]")
                            },
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true, widthDp = 320, heightDp = 400)
@Composable
fun DefaultPreview() {
    CheckersBoardTheme {
        CheckersBoards("Android")
    }
}