package com.wafflestudio.bunnybunny.utils

import com.google.gson.Gson
import com.squareup.moshi.Json
import com.wafflestudio.bunnybunny.data.example.RecentMessagesResponse
import kotlinx.serialization.decodeFromString


fun fromStringToRecentMessagesResponse(jsonString: String): RecentMessagesResponse {
    val gson = Gson()

    // JSON 문자열을 Person 객체로 변환
    val response: RecentMessagesResponse = gson.fromJson(jsonString, RecentMessagesResponse::class.java)
    return response
}

fun mutableListToString(mutableList: MutableList<Int>): String {
        return mutableList.joinToString(",")
}

    // 문자열을 MutableList로 변환하는 함수
fun stringToMutableList(str: String): MutableList<Int> {
        return str.split(",").map { it.toInt() }.toMutableList()
}

