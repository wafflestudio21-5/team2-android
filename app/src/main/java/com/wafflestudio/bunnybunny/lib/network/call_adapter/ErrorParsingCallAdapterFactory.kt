package com.wafflestudio.bunnybunny.lib.network.call_adapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ErrorParsingCallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        TODO("Not yet implemented")
    }
}