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
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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

@ExperimentalFoundationApi
@Composable
fun CheckersBoards(name: String) {
    val row = 8
    val cols = 8
    val board = Array(row) { IntArray(cols) }

    val items: List<String> = (0..7).map { "$it" }
    LazyColumn {
        itemsIndexed(items) { rowIndex: Int, item: String ->
            Row {
                (0..7).map { colIndex: Int ->
                    val color = if (rowIndex % 2 == 0) {
                        if (colIndex % 2 == 0) Color.White else Color.Gray
                    } else {
                        if (colIndex % 2 != 0) Color.White else Color.Gray
                    }

                    Text(
                        textAlign = TextAlign.Center,
                        text = AnnotatedString("$item:$colIndex"),
                        modifier = Modifier
                            .background(color = color)
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