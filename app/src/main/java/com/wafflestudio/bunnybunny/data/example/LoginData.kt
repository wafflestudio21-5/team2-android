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

data class SignupRequest(
    @Json(name = "email") val email : String,
    @Json(name = "password") val password : String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileImage") val profileImage: String?,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>,
)

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "provider") val provider: String,
    @Json(name = "sub") val sub: String,
    @Json(name = "role") val role: String,
    @Json(name = "profileImageUrl") val profileImageUrl: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "mannerTemp") val mannerTemp: Int,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String,
)