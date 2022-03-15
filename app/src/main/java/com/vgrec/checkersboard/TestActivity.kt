package com.vgrec.checkersboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vgrec.checkersboard.ui.theme.CheckersBoardTheme

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CheckersBoardTheme {
                Surface(
                    color = MaterialTheme.colors.background) {
                    val openDialog = rememberSaveable {
                        mutableStateOf(false)
                    }

                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            openDialog.value = true
                        }
                    ) {
                        Text(text = "HelloWorld")
                    }

                    if (openDialog.value) {
                        SettingsPopup(onDismiss = {
                            openDialog.value = false
                        })
                    }
                }
            }
        }
    }

    @Composable
    private fun SettingsPopup(onDismiss: () -> Unit) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = "Settings")
            },
            text = {
                Column {
                    Text(text = "row 1")
                    Text(text = "row 2")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Save")
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Cancel")
                    }
                }
            }
        )
    }

}