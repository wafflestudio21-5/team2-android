package com.wafflestudio.bunnybunny.components.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wafflestudio.bunnybunny.data.example.RecentMessagesResponse
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatRoomScreen(viewModel: ChatViewModel) {
    var cur by remember { mutableStateOf(255) }
    val coroutineScope = rememberCoroutineScope()
    val messages by viewModel.messages.collectAsState()

    LazyColumn {
        items(messages) { message ->
            Text(text = message.second)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // UI 내용은 여기에 추가

        Button(
            onClick = {
                try {
                    coroutineScope.launch {
                        val websocket = viewModel.connectToChatRoom(5)
                        viewModel.getRecentMessages(websocket, 255)
                        Log.d("CHAT", "$websocket" )
                    }
                } catch (e: Exception) {
                    Log.d("CHAT", e.message!!)
                }
                // 버튼 클릭 시 viewModel의 함수 호출

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Get Recent Messages")
        }
    }
}