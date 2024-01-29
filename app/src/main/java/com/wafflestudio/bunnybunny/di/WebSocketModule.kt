package com.wafflestudio.bunnybunny.di

import android.content.Context
import android.content.SharedPreferences
import com.wafflestudio.bunnybunny.lib.network.BunnyUserWebSocketListener
import com.wafflestudio.bunnybunny.lib.network.BunnyWebSocketListener
import com.wafflestudio.bunnybunny.lib.network.MessageStorage
import com.wafflestudio.bunnybunny.lib.network.WebServicesProvider
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.viewModel.ChatViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebSocketModule {
    @Provides
    @Singleton
    @Named("WebSocketOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(39, TimeUnit.SECONDS)
            .build()    }

    @Provides
    @Singleton
    fun provideMessageStorage(): MessageStorage {
        return MessageStorage()
    }
    @Provides
    fun provideWebSocketListener(
        messageStorage: MessageStorage
    ): BunnyWebSocketListener {
        return BunnyWebSocketListener(messageStorage)    }

    @Provides
    fun provideUserWebSocketListener(
        messageStorage: MessageStorage
    ): BunnyUserWebSocketListener {
        return BunnyUserWebSocketListener(messageStorage)    }

//    @Provides
//    fun provideWebSocketForChannel(
//        sharedPreference: SharedPreferences,
//        okHttpClient: OkHttpClient,
//        webSocketListener: BunnyWebSocketListener,
//        @ChannelId channelId: Long,
//    ): WebSocket {
//        val address = "43.202.236.170:8080"
//        val token = sharedPreference.getString("originalToken", "")
//        val webSocketUrl = "ws://$address/ws/channels/$channelId?token=$token"
//
//        val request = Request.Builder()
//            .url(webSocketUrl)
//            .build()
//
//        return okHttpClient.newWebSocket(request, webSocketListener)
//    }
//
//    @Provides
//    fun provideWebSocketForUser(
//        sharedPreference: SharedPreferences,
//        okHttpClient: OkHttpClient,
//        webSocketListener: BunnyWebSocketListener,
//    ): WebSocket {
//        val address = "43.202.236.170:8080"
//        val token = sharedPreference.getString("originalToken", "")
//        val webSocketUrl = "ws://$address/ws/users?token=$token"
//
//        val request = Request.Builder()
//            .url(webSocketUrl)
//            .build()
//
//        return okHttpClient.newWebSocket(request, webSocketListener)
//    }

    @Provides
    @Singleton
    fun provideWebServicesProvider(
        messageStorage: MessageStorage,
        sharedPreference: SharedPreferences,
        @Named("WebSocketOkHttpClient") okHttpClient: OkHttpClient,
        webSocketListener: BunnyWebSocketListener,
        userWebsocketListener: BunnyUserWebSocketListener
    ): WebServicesProvider {
        return WebServicesProvider(messageStorage, sharedPreference, okHttpClient, webSocketListener, userWebsocketListener)    }}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ChannelId