package com.example.composeverifiableinput.component

import android.util.Patterns
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
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

@Composable
fun VerifiableField(
    label: String,
    success: ((text: String) -> Boolean)? = null,
    errorText: String? = null
) {
    var text by remember { mutableStateOf("") }
    var focused by remember { mutableStateOf(false) }

    val isError = success != null && !success(text) && !focused && text.isNotEmpty()

    val labelFontSize by animateFloatAsState(targetValue = if (text.isNotBlank() || focused) 12f else 18f)
    val labelOffset by animateDpAsState(targetValue = if (text.isNotBlank() || focused) 10.dp else 25.dp)
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
        Modifier
            .height(100.dp)
            .padding(start = 20.dp, end = 20.dp),
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
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                textDecoration = TextDecoration.None
            ),
            decorationBox = { textField ->
                Column {
                    Text(
                        modifier = Modifier
                            .offset(y = labelOffset)
                            .height(25.dp),
                        text = if (text.isNotEmpty() || focused) label.uppercase() else label,
                        color = if (isError) "#ff6d64".color else Color.Gray,
                        fontSize = labelFontSize.sp,
                        fontWeight = if (text.isNotEmpty() || focused) FontWeight.Bold else FontWeight.Normal
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(0.9f)) {
                            textField()
                        }
                        if (text.isNotEmpty() && !focused && success != null) {
                            Icon(
                                modifier = Modifier.weight(0.1f),
                                imageVector = if (success(text)) Icons.Default.Check else Icons.Default.Close,
                                tint = if (success(text)) "#66c95b".color else "#ff6d64".color,
                                contentDescription = ""
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(top = 5.dp),
                        color = Color.Black,
                        thickness = dividerSize
                    )
                    if (isError && !errorText.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            text = errorText,
                            color = "#ff6d64".color
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerifiableFieldPreview() {
    VerifiableField(
        label = "Test",
        success = { it.isEmpty() },
        errorText = "Boş Olamazs"
    )
}