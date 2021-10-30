package com.example.composeverifiableinput

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeverifiableinput.base.color
import com.example.composeverifiableinput.component.VerifiableField
import com.example.composeverifiableinput.component.VerifiableField2
import com.example.composeverifiableinput.ui.theme.ComposeVerifiableInputTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVerifiableInputTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityUI()
                }
            }
        }
    }
}

@Composable
fun MainActivityUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        VerifiableField(
            label = "E-posta ya da telefon numarası",
            success = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            errorText = "Burada bir yanlışlık var!"
        )
        VerifiableField2(
            label = "E-posta ya da telefon numarası",
            success = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            errorText = "Burada bir yanlışlık var!"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeVerifiableInputTheme {
        MainActivityUI()
    }
}