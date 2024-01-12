package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Timestamp

data class GoodsPostPreview(
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: String,
    @Json(name = "repImg") val repImg: String,
    @Json(name = "sellPrice") val sellPrice: Int,
    @Json(name = "refreshedAt") val refreshedAt: Timestamp,
    @Json(name = "createdAt") val createdAt: Timestamp,
    @Json(name = "wishCnt") val wishCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "sellingArea") val tradingLocation: String,
)
data class GoodsPostPreviewList(
    @Json(name = "data") val data: List<GoodsPostPreview>,

)
