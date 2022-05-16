package com.vgrec.checkersboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vgrec.checkersboard.model.Piece
import com.vgrec.checkersboard.model.PieceColor
import com.vgrec.checkersboard.model.Position
import com.vgrec.checkersboard.model.Square
import com.vgrec.checkersboard.model.isNotEmpty
import com.vgrec.checkersboard.ui.theme.CheckersBoardTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        startActivity(Intent(this, TestActivity::class.java))

        setContent {
            CheckersBoardTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Box(
                        modifier = Modifier
                            .background(color = SCREEN_BACKGROUND_COLOR)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        CheckersBoard(viewModel = mainViewModel)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CheckersBoard(viewModel: MainViewModel) {
    val board = viewModel.uiState.board
    val validPositions = viewModel.uiState.validPositions

    LazyColumn {
        items((0 until BOARD_SIZE).toList()) { rowIndex: Int ->
            Row {
                (0 until BOARD_SIZE).map { colIndex: Int ->
                    val currentPosition = Position(
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                    val square: Square = board[currentPosition]

                    SquareView(
                        square = square,
                        currentPosition = currentPosition,
                        validPositions = validPositions,
                        squareWidth = viewModel.calculateSquareWidth(),
                        onViewClickedListener = {
                            viewModel.handleClickAtPosition(
                                position = currentPosition
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SquareView(
    square: Square,
    currentPosition: Position,
    validPositions: List<Position>,
    squareWidth: Int,
    onViewClickedListener: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = square.color)
            .width(squareWidth.dp)
            .height(squareWidth.dp)
            .clickable {
                onViewClickedListener()
            },
        contentAlignment = Alignment.Center
    ) {
        if (square.isNotEmpty()) {
            val piece: Piece = square.piece!!
            if (piece.color == PieceColor.LIGHT) {
                SimpleCircleShape(
                    diameter = 30.dp,
                    color = Color(0xfff0eee1)
                )
            } else { // DARK
                SimpleCircleShape(
                    diameter = 30.dp,
                    color = Color(0xff473d3a)
                )
            }
        }

        if (validPositions.contains(currentPosition)) {
            SimpleCircleShape(
                diameter = 15.dp,
                color = Color.DarkGray.copy(alpha = 0.4f)
            )
        }

        if (square.number != null) {
            Text(
                text = square.number.toString(),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Composable
private fun SimpleCircleShape(diameter: Dp, color: Color) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = color)
            .width(diameter)
            .height(diameter)
    )
}