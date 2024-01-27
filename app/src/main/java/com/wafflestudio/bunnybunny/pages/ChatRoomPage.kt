package com.wafflestudio.bunnybunny.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wafflestudio.bunnybunny.components.compose.ChatRoomScreen
import com.wafflestudio.bunnybunny.di.ChannelId
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel

@Composable
fun ChatRoomPage (modifier: Modifier, viewModel: ChatViewModel, channelId: Long) {
    ChatRoomScreen(viewModel = viewModel, channelId)
}