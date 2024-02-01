package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.wafflestudio.bunnybunny.components.UI.bunnyColor

@Composable
fun BunnyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    fontSize: TextUnit = 16.sp,
    textColor: Color = Color.White,
    networkBoolean: Boolean = true,
    buttonColor: Color = bunnyColor,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        enabled = networkBoolean,
    ) {
        Text(text, color = textColor, fontSize = fontSize)
    }
}
