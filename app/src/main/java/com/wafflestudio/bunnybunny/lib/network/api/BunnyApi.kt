package com.wafflestudio.bunnybunny.lib.network.api

import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface BunnyApi {

    /*@GET("/test/auth")
    suspend fun testLogin(@Header("Authorization") token: String): Boolean*/

    @POST("/auth/login")
    suspend fun loginRequest(
        @Body request: LoginRequest) : LoginResponse

}