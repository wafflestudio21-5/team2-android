package com.wafflestudio.bunnybunny.lib.network

import android.content.SharedPreferences
import android.util.Log
import com.wafflestudio.bunnybunny.data.example.RecentMessagesResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class WebServicesProvider @Inject constructor(
    private val sharedPreference: SharedPreferences,
    @Named("WebSocketOkHttpClient") private val okHttpClient: OkHttpClient,
    private val webSocketListener: BunnyWebSocketListener,
) {
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _messages = MutableStateFlow(emptyList<Pair<Boolean, String>>())
    val messages = _messages.asStateFlow()

    fun connectChannel(channelId: Long): WebSocket {
        val address = "banibani.shop"
        val token = sharedPreference.getString("originalToken", "") // token 값 가져옴
        val webSocketUrl =
            "ws://$address/ws/channels/$channelId?token=$token"
        Log.d("WSP", "$webSocketUrl")
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        return okHttpClient.newWebSocket(request, webSocketListener)
    }

    fun connectUser(): WebSocket {
        val address = "banibani.shop"
        val token = sharedPreference.getString("originalToken", "")
        val webSocketUrl = "ws://$address/ws/users?token=$token"

        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        return okHttpClient.newWebSocket(request, webSocketListener)
    }

    fun disconnectChannel(webSocket: WebSocket) {
        webSocket.close(1000, "Disconnected by client")
    }

    fun shutdown() {
        okHttpClient.dispatcher.executorService.shutdown()
    }


    fun sendRecentMessageRequest(channelWebSocket: WebSocket, cur: Int) {
        // RECENT_MESSAGE 메시지 포맷 작성
        val formattedMessage = "RECENT_MESSAGE\ncur:$cur\n"

        // channelWebSocket을 사용하여 메시지 전송
        channelWebSocket.send(formattedMessage)
    }

    fun sendTextMessage(channelWebSocket: WebSocket, body: String) {
        // SEND_TEXT 메시지 포맷 작성
        val formattedMessage = "SEND_TEXT\n\n$body\n"

        // channelWebSocket을 사용하여 메시지 전송
        channelWebSocket.send(formattedMessage)
    }


}