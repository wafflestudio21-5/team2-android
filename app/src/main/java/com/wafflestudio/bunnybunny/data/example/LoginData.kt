package com.wafflestudio.bunnybunny.data.example

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class LoginRequest(
    @Json(name = "email") val email : String,
    @Json(name = "password") val password : String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "uid") val uid: Int,
    @Json(name = "token") val token : String,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>,
    @Json(name = "isAdmin") val isAdmin: Boolean
)