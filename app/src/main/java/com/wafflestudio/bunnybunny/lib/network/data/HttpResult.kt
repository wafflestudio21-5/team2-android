package com.wafflestudio.bunnybunny.lib.network.data

sealed class HttpResult<T> {
    class Success<T>(val data: T, val code: Int) : HttpResult<T>()
    class ApiError<T>(val message: String, val code: Int) : HttpResult<T>()
    class NetworkError<T>(val throwable: Throwable) : HttpResult<T>()
    class NullResult<T> : HttpResult<T>()
}
