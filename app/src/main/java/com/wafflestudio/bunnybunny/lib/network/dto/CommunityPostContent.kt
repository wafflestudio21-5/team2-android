package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityPostContent(
    @Json(name = "community") val community: Community,
    @Json(name = "isLiked") var isLike: Boolean,
    @Json(name = "nickname") val authorName: String,
    @Json(name = "profileImg") val profileImg: String?,
    @Json(name = "areaInfo") val areaName: String,

    )
@JsonClass(generateAdapter = true)
data class Community(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "areaId") val areaId: Long,
    @Json(name = "authorId") val authorId: Long,
    @Json(name = "images") val images: List<String>,
    @Json(name = "viewCnt") val viewCnt: Int,
    @Json(name = "likeCnt") var likeCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "createdAt") val createdAt: Long,
)
