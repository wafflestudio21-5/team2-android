package com.wafflestudio.bunnybunny.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafflestudio.bunnybunny.data.example.ChatChannel
import com.wafflestudio.bunnybunny.data.example.ChatListResponse
import com.wafflestudio.bunnybunny.data.example.RecentMessagesResponse
import com.wafflestudio.bunnybunny.lib.network.WebServicesProvider
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.WebSocket
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: BunnyApi,
    private val sharedPreference: SharedPreferences,
    private val webServicesProvider: WebServicesProvider
): ViewModel() {

    val messages: StateFlow<List<Pair<Boolean, String>>> = webServicesProvider.messages

    private val _chatListResponse = MutableStateFlow(ChatListResponse(listOf<ChatChannel>(), listOf<ChatChannel>()));
    val chatListResponse : StateFlow<ChatListResponse> = _chatListResponse.asStateFlow()

    suspend fun connectToChatRoom(channelId: Long): WebSocket {
        return webServicesProvider.connectChannel(channelId)
    }

    fun disconnectFromChatRoom(websocket: WebSocket) {
        // 웹소켓 연결 종료
        webServicesProvider.disconnectChannel(websocket)
    }

    suspend fun getRecentMessages(websocket: WebSocket, cur: Int) {
        webServicesProvider.sendRecentMessageRequest(websocket, 255)
    }

    fun sendMessage(websocket: WebSocket, message: String) {
        // 메시지 전송
        webServicesProvider.sendTextMessage(websocket, message)
    }

    fun getChannelList() {
        val token = sharedPreference.getString("token", "")!!
        Log.d("CTPV", token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.chatChannelRequest(token)
                _chatListResponse.value = response
                Log.d("CTPV", "try" + response.toString())
            } catch (e: HttpException) {
                val message = e.response()?.code().toString()
                Log.d("CTPV", "catch" + message)
            }
        }
    }



}