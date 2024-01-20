package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wafflestudio.bunnybunny.data.example.RefAreaId
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData

@Composable
fun AreaBox(modifier: Modifier, areaDetail: SimpleAreaData) {
    Box {
        Row (modifier = Modifier
            .fillMaxWidth()
            .drawWithContent {
            drawContent()
            drawRect(
                color = Color.Gray,
                topLeft = Offset(0f, size.height - 1.dp.toPx()), // 맨 아래에만 테두리 추가
                size = Size(size.width, 1.dp.toPx())
            )}
            .padding(16.dp)) {
            Text (modifier = Modifier, text = areaDetail.fullName, fontSize = 24.sp)
        }
    }
}