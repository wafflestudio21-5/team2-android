package com.wafflestudio.bunnybunny.lib.network

import android.content.SharedPreferences
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

interface WebSocketManager {
    fun createChannelWebSocket(channelId: Long)

    fun createUserWebSocket()
    fun disposeWebSocket()
    fun isWebSocketAvailable(): Boolean

    fun sendMessage(message: String)
    fun sendRecentMessageRequest(cur: Int)
    fun sendTextMessage(body: String)
}

class WebSocketManagerImpl @Inject constructor(
    private val sharedPreference: SharedPreferences,
    private val okHttpClient: OkHttpClient,
    private val webSocketListener: BunnyWebSocketListener,
): WebSocketManager {
    var webSocket: WebSocket? = null
    var userWebSocket: WebSocket? = null

    override fun createChannelWebSocket(channelId: Long) {
        val address = "banibani.shop"
        val token = sharedPreference.getString("originalToken", "") // token 값 가져옴
        val webSocketUrl =
            "ws://$address/ws/channels/$channelId?token=$token"
        Log.d("WSP", "$webSocketUrl")
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()
        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    override fun createUserWebSocket(){
        val address = "banibani.shop"
        val token = sharedPreference.getString("originalToken", "")
        val webSocketUrl = "ws://$address/ws/users?token=$token"

        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    override fun disposeWebSocket() {
        webSocket?.close(1000, "Disconnected by client")
        webSocket = null
    }

    override fun isWebSocketAvailable(): Boolean {
        return webSocket != null
    }

    override fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    override fun sendRecentMessageRequest(cur: Int)  {
        // RECENT_MESSAGE 메시지 포맷 작성
        val formattedMessage = "RECENT_MESSAGE\ncur:$cur\n"
        webSocket?.send(formattedMessage)
    }

    override fun sendTextMessage(body: String) {
        // SEND_TEXT 메시지 포맷 작성
        val formattedMessage = "SEND_TEXT\n\n$body\n"

        // channelWebSocket을 사용하여 메시지 전송
        webSocket?.send(formattedMessage)
    }
}