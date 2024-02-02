package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.wafflestudio.bunnybunny.data.example.ChatChannel
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel

@Composable
fun ChatBox(modifier: Modifier, chatChannel: ChatChannel, viewModel: ChatViewModel, navController: NavController) {

//    val unreads = viewModel.getUnreadMessagesInChannel(chatChannel.channelId)
//            Column {
//
//                Text(
//
//                    // 보낸 메시지
//                    text = if (unreads != 0) unreads.toString() else "",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//
//            }
//
//        }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable() {
                navController.navigate("ChatRoomPage/${chatChannel.channelId}")
            }
            .height(72.dp)
            .padding(16.dp)
            .background(color = Color(0xFFFFFFFF))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 40.dp))
        ) {
            if (chatChannel.profileImg!=null) {
                Image(
                    painter = rememberImagePainter(
                        data = chatChannel.profileImg,
                        builder = {
                            transformations(CircleCropTransformation())
                        }),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds
                )
            } else {
                // 사용자 기본 이미지 추가하기
                Image(
                    painter = rememberImagePainter(
                        data = "https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png",
                        builder = {
                            transformations(CircleCropTransformation())
                        }),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Column(
            modifier = Modifier
                .width(246.dp)
                .height(40.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier
                        .width(40.dp)
                        .height(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = chatChannel.nickname,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
//                            fontFamily = FontFamily(Font(res.font.inter)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .width(158.dp)
                        .height(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = chatChannel.lastMsg,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
//                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 4.dp))
        ) {
            // 물품 사진 관련한 데이터로 추후 변경하기
            Image(
                painter = rememberImagePainter(
                    data = chatChannel.profileImg,
                    builder = {
                        transformations(CircleCropTransformation())
                    }),
                contentDescription = "goods image description",
                contentScale = ContentScale.FillBounds
            )
        }
        }


    }
}