package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoodsPostPreview(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "repImg") val repImg: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "refreshedAt") val refreshedAt: Long,
    @Json(name = "wishCnt") val wishCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "sellPrice") val sellPrice: Int,
    @Json(name = "sellingArea") val tradingLocation: String,
    @Json(name = "deadline") val deadline: Long,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: String,

)
@JsonClass(generateAdapter = true)
data class GoodsPostList(
    @Json(name = "data") val data: List<GoodsPostPreview>,
    @Json(name = "cur") val cur: Long?,
    @Json(name = "seed") val seed: Int?,
    @Json(name = "isLast") val isLast: Boolean,
    @Json(name = "count") val count: Int?,
    )
