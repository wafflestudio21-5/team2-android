package com.wafflestudio.bunnybunny.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.ChatListResponse
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: BunnyApi
): ViewModel() {

    fun getChannelList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.chatChannelRequest()
                Log.d("CTPV", response.toString())
            } catch (e: HttpException) {
                val message = e.response()?.code().toString()
                Log.d("CTPV", message)
            }
        }
    }

}