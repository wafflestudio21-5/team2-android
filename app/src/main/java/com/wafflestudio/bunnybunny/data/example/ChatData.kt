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

@JsonClass(generateAdapter = true)
data class CreateChatRoomRequest(
    @Json(name = "postId") val postId: Long
)

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "msgNo") val msgNo: Long,
    @Json(name = "isMine") val isMine: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "createdAt") val createdAt: Long
)

@JsonClass(generateAdapter = true)
data class ChannelInfo(
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileImg") val profileImg: String,
    @Json(name = "repImg") val repImg: String,
    @Json(name = "title") val title: String,
    @Json(name = "sellPrice") val sellPrice: Int,
    @Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class RecentMessagesResponse(
    @Json(name = "messages") val messages: List<Message>,
    @Json(name = "cur") val cur: Long,
    @Json(name = "channel_info") val channelInfo: ChannelInfo
)

@JsonClass(generateAdapter = true)
data class NewUserMessageResponse(
    @Json(name = "channelId") val channelId: Long,
    @Json(name = "message") val message: String,
    @Json(name = "createdAt") val createdAt: Long
)