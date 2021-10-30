package com.example.composeverifiableinput.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeverifiableinput.base.color

@Composable
fun VerifiableField2(
    label: String,
    success: ((text: String) -> Boolean)? = null,
    errorText: String? = null
) {
    var text by remember { mutableStateOf("") }
    var focused by remember { mutableStateOf(false) }

    val colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        textColor = Color.Black,
        cursorColor = Color.Black,
        focusedLabelColor = Color.Gray,
        unfocusedLabelColor = Color.Gray,
        errorLabelColor = "#ff6d64".color,
        focusedIndicatorColor = Color.Black,
        unfocusedIndicatorColor = Color.Gray,
        errorIndicatorColor = Color.Black,
        errorTrailingIconColor = "#ff6d64".color
    )

    val isError = success != null && !success(text) && !focused && text.isNotEmpty()

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    focused = it.isFocused
                },
            colors = colors,
            value = text,
            onValueChange = { text = it },
            label = {
                Text(
                    text = if (focused || text.isNotEmpty()) label.uppercase() else label,
                    fontWeight = if (focused || text.isNotEmpty()) FontWeight.Bold else FontWeight.Normal
                )
            },
            trailingIcon = {
                if (text.isNotEmpty() && !focused && success != null) {
                    Icon(
                        imageVector = if (success(text)) Icons.Default.Check else Icons.Default.Close,
                        tint = if (success(text)) "#66c95b".color else "#ff6d64".color,
                        contentDescription = ""
                    )
                }
            },
            isError = isError
        )
        if (isError && !errorText.isNullOrEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 16.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                text = errorText,
                color = "#ff6d64".color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifiableFieldPreview2() {
    VerifiableField2(
        label = "Test",
        success = { it.isEmpty() },
        errorText = "Boş Olamazs"
    )
}