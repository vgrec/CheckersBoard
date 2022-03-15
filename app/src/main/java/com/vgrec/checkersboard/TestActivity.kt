package com.vgrec.checkersboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.vgrec.checkersboard.ui.theme.CheckersBoardTheme

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CheckersBoardTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "HelloWorld")
                    }
                }
            }
        }
    }

}