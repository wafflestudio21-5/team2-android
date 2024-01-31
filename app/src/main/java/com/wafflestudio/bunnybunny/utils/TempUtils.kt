package com.wafflestudio.bunnybunny.utils

import androidx.compose.ui.graphics.Color

fun calculateMannerTempColor(temp: Double): Color {
    return when {
        temp < 37 -> Color(0xFF00D4C2)
        temp < 40 -> Color(0xFF8BC34A)
        temp >= 40 -> Color(0xFFD4003C)
        else -> Color(0xFF0035D4)
    }
}