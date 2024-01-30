package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wafflestudio.bunnybunny.data.example.Message
import com.wafflestudio.bunnybunny.ui.theme.PurpleGrey80


@Composable
fun ChatBubble(modifier: Modifier, message: Message) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(
                    if (message.isMine) Alignment.End else Alignment.Start
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isMine) 48f else 0f,
                        bottomEnd = if (message.isMine) 0f else 48f
                    )
                )
                .background(PurpleGrey80)
                .padding(16.dp)
        ) {
            Text(text = message.message)
        }
    }
}