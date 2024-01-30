package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoodsPostContent(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: String,
    @Json(name = "authorId") val authorId: Long,
    @Json(name = "authorName") val authorName: String,
    @Json(name = "authorMannerTemperature") val authorMannerTemperature: Double = 36.5,
    @Json(name = "buyerId") val buyerId: Long,
    @Json(name = "sellingArea") val sellingArea: String,
    @Json(name = "sellPrice") val sellPrice: Int,
    @Json(name = "profileImg") val profileImg: String,
    @Json(name = "images") val images: List<String>,
    @Json(name = "viewCnt") val viewCnt: Int,
    @Json(name = "offerYn") val offerYn: Boolean,
    @Json(name = "refreshCnt") val refreshCnt: Int,
    @Json(name = "refreshedAt") val refreshedAt: Long,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "deadline") val deadline: Long,
    @Json(name = "wishCnt") var wishCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "isWish") var isWish: Boolean,
)
