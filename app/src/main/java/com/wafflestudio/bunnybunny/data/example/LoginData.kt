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

@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "email") val email : String,
    @Json(name = "password") val password : String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileImage") val profileImage: String?,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>,
)

data class SocialLoginRequest(
    @Json(name = "idToken") val idToken: String,
)

data class SocialSignupRequest(
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileImage") val profileImage: String?,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>,
    @Json(name = "idToken") val idToken: String
)

@JsonClass(generateAdapter = true)
data class SignupResponse(
    @Json(name = "user") val user: UserInfo
)
@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "provider") val provider: String,
    @Json(name = "sub") val sub: String?,
    @Json(name = "role") val role: String,
    @Json(name = "profileImageUrl") val profileImageUrl: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "mannerTemp") val mannerTemp: Double,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "refAreaIds") val refAreaIds: List<RefAreaId>
)

@JsonClass(generateAdapter = true)
data class SocialUserInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String?,
    @Json(name = "provider") val provider: String,
    @Json(name = "sub") val sub: String?,
    @Json(name = "role") val role: String,
    @Json(name = "profileImageUrl") val profileImageUrl: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "mannerTemp") val mannerTemp: Int,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "refAreaIds") val refAreaIds: List<RefAreaId>
)


@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String,
)

@JsonClass(generateAdapter = true)
data class RefAreaId(
    @Json(name = "id") val id: Int,
    @Json(name = "code") val code: String,
    @Json(name = "fullName") val fullName: String,
    @Json(name = "name") val name: String,
    @Json(name = "sggName") val sggName: String,
    @Json(name = "sdName") val sdName: String,
    @Json(name = "authenticatedAt") val authenticatedAt: Long,
    @Json(name = "count") val count: Int
)