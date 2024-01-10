package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class GoodsPostContent(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: String,
    @Json(name = "authorId") val authorId: Long,
    @Json(name = "buyerId") val buyerId: Long,
    @Json(name = "sellingArea") val sellingArea: String,
    @Json(name = "repImg") val repImg: String,
    @Json(name = "images") val images: List<String>,
    @Json(name = "viewCnt") val viewCnt: Int,
    @Json(name = "offerYn") val offerYn: Boolean,
    @Json(name = "refreshCnt") val refreshCnt: Int,
    @Json(name = "refreshedAt") val refreshedAt: Long,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "deadline") val deadline: Long,
    @Json(name = "hiddenYn") val hiddenYn: Boolean,
    @Json(name = "sellPrice") val sellPrice: Int,
    @Json(name = "wishCnt") val wishCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
)
