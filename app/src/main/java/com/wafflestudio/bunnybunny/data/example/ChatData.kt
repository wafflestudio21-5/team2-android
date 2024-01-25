package com.wafflestudio.bunnybunny.data.example

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatChannel(
    @Json(name = "channelId") val channelId: Long,
    @Json(name = "profileImg") val profileImg: String?,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "activeArea") val activeArea: String,
    @Json(name = "lastMsg") val lastMsg: String,
    @Json(name = "msgUpdatedAt") val msgUpdatedAt: Double,
    @Json(name = "pinnedAt") val pinnedAt: Long?
)

@JsonClass(generateAdapter = true)
data class ChatListResponse(
    @Json(name = "pinned") val pinned: List<ChatChannel>,
    @Json(name = "normal") val normal: List<ChatChannel>
)