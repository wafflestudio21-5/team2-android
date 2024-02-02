package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubmitPostRequest(
    @Json(name = "areaId") val areaId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "type") val type: String,
    @Json(name = "repImg") val repImg: String?,
    @Json(name = "images") val images: List<String>?,
    @Json(name = "deadline") val deadline: Long,
    @Json(name = "offerYn") val offerYn: Boolean,
    @Json(name = "sellPrice") val sellPrice: Int,
)

@JsonClass(generateAdapter = true)
data class SubmitCommunityPostRequest(
    @Json(name = "areaId") val areaId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "repImg") val repImg: String?,
    @Json(name = "images") val images: List<String>?,
)

@JsonClass(generateAdapter = true)
data class postImagesResponse(
    @Json(name = "images") val images: List<String>,
)