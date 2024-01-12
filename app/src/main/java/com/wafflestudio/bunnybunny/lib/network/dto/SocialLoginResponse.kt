package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json

data class SocialLoginResponse(
    @Json(name = "uid") val uid: Int,
    @Json(name = "token") val token: String,
    @Json(name = "refAreaIds") val refAreaIds: List<Int>,
    @Json(name = "isAdmin") val isAdmin: Boolean
)