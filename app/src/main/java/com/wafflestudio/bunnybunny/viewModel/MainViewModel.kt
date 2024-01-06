package com.wafflestudio.bunnybunny.viewModel

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: BunnyApi
): ViewModel() {
    //홈 탭:Home
    //동네생활 탭: Community
    //채팅 탭:Chat
    //나의당근 탭:My
    var currentTab= mutableStateOf("Home")

    val goodsPostList : MutableState<List<GoodsPostPreview>> = mutableStateOf(listOf())
    companion object {}
    suspend fun tryLogin(email: String, password: String): LoginResponse{
            return api.loginRequest(LoginRequest(email, password))
    }

}