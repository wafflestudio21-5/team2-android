package com.wafflestudio.bunnybunny.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.AreaSearchResponse
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.RefAreaId
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: BunnyApi
): ViewModel() {
    //홈 탭:Home,0
    //동네생활 탭:Community,1
    //채팅 탭:Chat,2
    //나의당근 탭:My,3
    var selectedTabIndex= mutableStateOf(0)

    val goodsPostList : MutableState<List<GoodsPostPreview>> = mutableStateOf(listOf())

    private val _areaDetails: MutableStateFlow<List<SimpleAreaData>> = MutableStateFlow(listOf())
    val areaDetails: StateFlow<List<SimpleAreaData>> = _areaDetails.asStateFlow()

    companion object {}
    suspend fun tryLogin(email: String, password: String): LoginResponse {
            return api.loginRequest(LoginRequest(email, password))
    }
    suspend fun trySignup(data: SignupRequest): UserInfo{
        return api.signupRequest(data)
    }

    suspend fun trySocialLogin(data: SocialLoginRequest): SocialLoginResponse {
        return api.socialLoginRequest(data, "kakao")
    }

    suspend fun trySocialSignUp(data: SocialSignupRequest): UserInfo {
        return api.socialSignUpRequest(data, "kakao")
    }

    suspend fun tryAreaSearch(query: String, cursor: Int): List<SimpleAreaData> {
        _areaDetails.value = api.areaSearch(query, cursor).areas;
        Log.d("VM", "${_areaDetails.value}")
        return _areaDetails.value;
    }


}