package com.wafflestudio.bunnybunny.data.example

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class MetaData(
    @Json(name = "isEnd") val isEnd: Boolean,
    @Json(name = "cursor") val cursor: Int,
    @Json(name = "totalCount") val totalCount: Int,
)

@JsonClass(generateAdapter = true)
data class AreaSearchResponse(
    @Json(name = "meta") val meta: MetaData,
    @Json(name = "areas") val areas: List<RefAreaId>
)