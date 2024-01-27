package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.wafflestudio.bunnybunny.data.example.ChatChannel

@Composable
fun ChatBox(modifier: Modifier, chatChannel: ChatChannel, navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clickable() {
                navController.navigate("ChatRoomPage/${chatChannel.channelId}")
            }
            .height(64.dp)
            .padding(8.dp)
    ) {
        Row {
            // 여기에 profile image가 null이면 기본 이미지가, 아니면 유저 이미지가 들어가야함
            if (chatChannel.profileImg!=null) {
                Image(
                    painter = rememberImagePainter(
                        data = chatChannel.profileImg,
                        builder = {
                            transformations(CircleCropTransformation())
                        }),
                    contentDescription = null, // contentDescription을 설정하세요.
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
//                    contentScale = ContentScale
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Column {
                Row {
                    Text(
                        // 보내는 사람이 보임!
                        text = chatChannel.nickname,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    // 보낸 메시지
                    text = chatChannel.lastMsg,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }



    }
}