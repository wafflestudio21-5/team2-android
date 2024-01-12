package com.wafflestudio.bunnybunny.lib.network.api

import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BunnyApi {

    @POST("/auth/login")
    suspend fun loginRequest(
        @Body request: LoginRequest) : LoginResponse

    @POST("/signup")
    suspend fun signupRequest(@Body request: SignupRequest): UserInfo

    @POST("/auth/login/{provider}")
    suspend fun socialLoginRequest(@Body request: SocialLoginRequest, @Path("provider") provider: String): SocialLoginResponse

    @POST("/signup/{provider}")
    suspend fun socialSignUpRequest(@Body request: SocialSignupRequest, @Path("provider") provider: String): UserInfo

}