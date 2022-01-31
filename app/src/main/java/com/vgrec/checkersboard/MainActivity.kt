package com.vgrec.checkersboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.ui.theme.CheckersBoardTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckersBoardTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CheckersBoard(viewModel = mainViewModel)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CheckersBoard(viewModel: MainViewModel) {
    val board = viewModel.uiState.board

    LazyColumn {
        items((0 until ROWS).toList()) { rowIndex: Int ->
            Row {
                (0 until COLS).map { colIndex: Int ->
                    val square: Square = board[rowIndex][colIndex]

                    val symbol: String = square.piece?.let { piece ->
                        if (piece.color == PieceColor.LIGHT) {
                            "L"
                        } else {
                            "D"
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
                                viewModel.handleClickAtPosition(
                                    Position(
                                        rowIndex = rowIndex,
                                        colIndex = colIndex
                                    )
                                )
                            },
                    )
                }
            }
        }
    }
}