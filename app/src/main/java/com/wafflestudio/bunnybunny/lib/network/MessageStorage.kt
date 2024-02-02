package com.wafflestudio.bunnybunny.lib.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageStorage @Inject constructor() {

    private val _latestMessage = MutableStateFlow<String>("")
    val latestMessage = _latestMessage.asStateFlow()

    private val _userLatestMessage = MutableStateFlow<String>("")
    val userLatestMessage = _latestMessage.asStateFlow()

    fun updateLatestMessage(message: String) {
        _latestMessage.value = message
    }

    fun deleteLatestMessage() {
        _latestMessage.value = ""
    }

    fun updateUserLatestMessage(message: String) {
        _userLatestMessage.value = message
    }

}