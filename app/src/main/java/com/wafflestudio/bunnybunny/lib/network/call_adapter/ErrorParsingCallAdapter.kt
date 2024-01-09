/*
package com.wafflestudio.bunnybunny.lib.network.api

import com.wafflestudio.bunnybunny.data.example.ErrorResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ErrorParsingCallAdapter<T>(
    private val successType: Type,
): CallAdapter<T, Call<ErrorResponse>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ErrorResponse> {
        return
    }
}*/
