package com.wafflestudio.bunnybunny.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.SampleData.DefaultGoodsPostContentSample
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: BunnyApi
): ViewModel() {
    //홈 탭:Home,0
    //동네생활 탭:Community,1
    //채팅 탭:Chat,2
    //나의당근 탭:My,3
    var selectedTabIndex= mutableStateOf(0)

    val goodsPostList : MutableState<List<GoodsPostPreview>> = mutableStateOf(listOf())

    val goodsPostContent : MutableState<GoodsPostContent> = mutableStateOf(DefaultGoodsPostContentSample)
    companion object {}
    suspend fun tryLogin(email: String, password: String): LoginResponse{
            return api.loginRequest(LoginRequest(email, password))
    }


}