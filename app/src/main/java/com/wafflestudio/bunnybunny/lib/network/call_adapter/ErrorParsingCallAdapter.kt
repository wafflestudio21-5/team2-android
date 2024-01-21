package com.wafflestudio.bunnybunny.lib.network.call_adapter

import com.wafflestudio.bunnybunny.lib.network.data.HttpResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ErrorParsingCallAdapter<T>(
    private val successType : Type
): CallAdapter<T, Call<HttpResult<T>>> {
    override fun adapt(call: Call<T>): Call<HttpResult<T>> = ErrorParsingCall(call)
    override fun responseType(): Type = successType
}