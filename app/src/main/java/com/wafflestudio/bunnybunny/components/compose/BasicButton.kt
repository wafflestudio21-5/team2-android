package com.wafflestudio.bunnybunny.components.compose

import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun BasicButton(modifier: Modifier, onClick: () -> Unit, text: String, networkBoolean: Boolean) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth(0.4f),
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(containerColor = Color(1.0f, 0.647f, 0.0f, 1.0f)),
        onClick = onClick
    ) {
        Text(text = text)
    }
}