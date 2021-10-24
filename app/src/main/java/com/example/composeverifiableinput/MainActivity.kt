package com.example.composeverifiableinput

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeverifiableinput.ui.theme.ComposeVerifiableInputTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVerifiableInputTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    VerifiableField()
                }
            }
        }
    }
}

@Composable
fun VerifiableField() {
    var text by remember { mutableStateOf("") }
    var focused by remember { mutableStateOf(false) }

    val labelFontSize by animateFloatAsState(targetValue = if (text.isNotBlank() || focused) 12f else 18f)
    val labelOffset by animateDpAsState(targetValue = if (text.isNotBlank() || focused) 0.dp else 25.dp)
    val dividerSize by animateDpAsState(targetValue = if (focused) (1.5).dp else (0.5).dp)

    val infiniteTransition = rememberInfiniteTransition()
    val value by infiniteTransition.animateFloat(
        0f,
        1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    focused = it.isFocused
                },
            value = text,
            onValueChange = { text = it },
            decorationBox = {
                Column {
                    Text(
                        modifier = Modifier
                            .offset(
                                y = labelOffset
                            )
                            .height(25.dp),
                        text = "Test",
                        color = Color.LightGray,
                        fontSize = labelFontSize.sp,
                        fontWeight = if (text.isNotBlank() || focused) FontWeight.Bold else FontWeight.Normal
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            maxLines = 1,
                            text = text,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        if (focused) {
                            Box(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .height(20.dp)
                                    .width((1.5).dp)
                                    .alpha(value)
                                    .background(color = Color.Black)
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(top = 5.dp),
                        color = Color.Black,
                        thickness = dividerSize
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeVerifiableInputTheme {
        VerifiableField()
    }
}