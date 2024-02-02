package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginInputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    borderColor: Color = Color(1.0f, 0.647f, 0.0f, 1.0f),
    backgroundColor: Color = Color(0x00000000),
    cornerRadius: Int = 20,
    fraction: Float = 0.9f
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(fraction)
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(percent = cornerRadius)
            ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(percent = cornerRadius)
                    )
                    .padding(12.dp),
            ) {
                if (value.isEmpty()) {
                    Text(placeholder, color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}