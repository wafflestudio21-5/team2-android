package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Timestamp

data class GoodsPostPreview(
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: String,
    @Json(name = "rep_img") val repImg: String,
    @Json(name = "sell_price") val sellPrice: Int,
    @Json(name = "refreshed_at") val refreshedAt: Timestamp,
    @Json(name = "wish_cnt") val wishCnt: Int,
    @Json(name = "chat_cnt") val chatCnt: Int,
    @Json(name = "trading_location") val tradingLocation: String,
)
data class GoodsPostPreviewList(
    @Json(name = "data") val data: List<GoodsPostPreview>,

)
