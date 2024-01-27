package com.wafflestudio.bunnybunny.lib.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageStorage @Inject constructor() {

    private val _latestMessage = MutableStateFlow<String>("")
    val latestMessage = _latestMessage.asStateFlow()

    fun updateLatestMessage(message: String) {
        _latestMessage.value = message
    }

}