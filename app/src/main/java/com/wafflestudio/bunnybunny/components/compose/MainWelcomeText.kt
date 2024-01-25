package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainWelcomeText() {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 기본 텍스트
        Text("반갑습니다, 버니!", modifier = Modifier.padding(8.dp), fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Text("동네라서 가능한 모든 것" + "\n" + "얼른 버니가 되세요!", fontSize = 16.sp, textAlign = TextAlign.Center)
    }
}
