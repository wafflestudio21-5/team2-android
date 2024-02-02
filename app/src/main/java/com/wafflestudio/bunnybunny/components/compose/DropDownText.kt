package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DropDownText(modifier: Modifier, text: String) {

    Text(modifier = modifier, text = text)
}