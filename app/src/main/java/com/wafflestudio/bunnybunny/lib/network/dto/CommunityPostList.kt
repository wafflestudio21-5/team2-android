package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityPostPreview(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "repImg") val repImg: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "viewCnt") val viewCnt: Int,
    @Json(name = "likeCnt") val likeCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "description") val description: String,
    @Json(name = "areaId") val areaId: Int,
    )
@JsonClass(generateAdapter = true)
data class CommunityPostList(
    @Json(name = "data") val data: List<CommunityPostPreview>,
    @Json(name = "cur") val cur: Long?,
    @Json(name = "seed") val seed: Int?,
    @Json(name = "isLast") val isLast: Boolean,
    @Json(name = "count") val count: Int?,
)

