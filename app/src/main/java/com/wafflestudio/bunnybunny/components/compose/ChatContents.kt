package com.wafflestudio.bunnybunny.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel

@Composable
fun ChatContents(modifier: Modifier, viewModel: ChatViewModel) {
//    val viewModel: ChatViewModel = hiltViewModel()
    val chatListResponse by viewModel.chatListResponse.collectAsState()
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color=Color.Red),
        verticalArrangement = Arrangement.Top) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(color = Color.Blue),
            verticalArrangement = Arrangement.Top
        ) {
            items(chatListResponse.pinned.size) { position ->
                ChatBox(
                    modifier = Modifier,
                    chatChannel = chatListResponse.pinned[chatListResponse.pinned.size - position - 1],
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(color = Color.Green),
            verticalArrangement = Arrangement.Top
        ) {
            items(chatListResponse.normal.size) { position ->
                ChatBox(
                    modifier = Modifier,
                    chatChannel = chatListResponse.normal[chatListResponse.normal.size - position - 1],
                )
            }
        }
    }
}