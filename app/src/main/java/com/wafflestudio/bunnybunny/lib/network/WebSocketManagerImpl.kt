package com.wafflestudio.bunnybunny.lib.network

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.bunnybunny.data.example.PrefRepository
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Named

interface WebSocketManager {
    suspend fun createChannelWebSocket(channelId: Long)

    suspend fun createUserWebSocket()
    suspend fun disposeWebSocket()
    suspend fun isWebSocketAvailable(): Boolean

    suspend fun sendMessage(message: String)
    suspend fun sendRecentMessageRequest(cur: Int)
    suspend fun sendTextMessage(body: String)
}

class WebSocketManagerImpl @Inject constructor(
    private val prefRepository: PrefRepository,
    @Named("WebSocketOkHttpClient") val okHttpClient: OkHttpClient,
    private val webSocketListener: BunnyWebSocketListener,
): WebSocketManager {
    var webSocket: WebSocket? = null
    var userWebSocket: WebSocket? = null

    override suspend fun createChannelWebSocket(channelId: Long) {
        val address = "banibani.shop"
        val token = prefRepository.getPref("token")
        val webSocketUrl =
            "ws://$address/ws/channels/$channelId?token=$token"
        Log.d("WSP", "$webSocketUrl")
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()
        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    override suspend fun createUserWebSocket(){
        val address = "banibani.shop"
        val token = prefRepository.getPref("token")
        val webSocketUrl = "ws://$address/ws/users?token=$token"

        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    override suspend fun disposeWebSocket() {
        webSocket?.close(1000, "Disconnected by client")
        webSocket = null
    }

    override suspend fun isWebSocketAvailable(): Boolean {
        return webSocket != null
    }

    override suspend fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    override suspend fun sendRecentMessageRequest(cur: Int)  {
        // RECENT_MESSAGE 메시지 포맷 작성
        val formattedMessage = "RECENT_MESSAGE\ncur:$cur\n"
        webSocket?.send(formattedMessage)
    }

    override suspend fun sendTextMessage(body: String) {
        // SEND_TEXT 메시지 포맷 작성
        val formattedMessage = "SEND_TEXT\n\n$body\n"

        // channelWebSocket을 사용하여 메시지 전송
        webSocket?.send(formattedMessage)
    }
}