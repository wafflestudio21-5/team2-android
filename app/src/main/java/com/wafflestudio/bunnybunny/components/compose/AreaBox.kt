package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.wafflestudio.bunnybunny.data.example.RefAreaId

@Composable
fun AreaBox(modifier: Modifier, areaDetail: RefAreaId) {
    Box {
        Row (modifier = Modifier.background(Color.Gray).fillMaxWidth()) {
            Text (modifier = Modifier, text = areaDetail.fullName)
        }
    }
}