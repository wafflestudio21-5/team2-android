package com.wafflestudio.bunnybunny.lib.network.dto

import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiOnError @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,) {
    @JsonClass(generateAdapter = true)
    data class ErrorDTO(
        @Json(name = "code") val code: Int? = null,
        @Json(name = "message") val message: String? = null,
        @Json(name = "ext") val ext: Map<String, String>? = null,
    )
}