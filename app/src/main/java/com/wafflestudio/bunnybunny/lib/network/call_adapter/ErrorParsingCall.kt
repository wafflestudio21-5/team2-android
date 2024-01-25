package com.wafflestudio.bunnybunny.lib.network.call_adapter

import android.util.Log
import com.wafflestudio.bunnybunny.lib.network.data.HttpResult
import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ErrorParsingCall<T> constructor(
    private val delegate: Call<T>
): Call<HttpResult<T>> {
    override fun enqueue(callback: Callback<HttpResult<T>>) = delegate.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val jObjError = JSONObject(response.errorBody()!!.string()).getJSONObject("error")
                val code = jObjError.getString("code").toInt()
                val message = jObjError.getString("message")
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            callback.onResponse(this@ErrorParsingCall, Response.success(HttpResult.Success(it, response.code())))
                        }
                        in 400..410 -> {
                            callback.onResponse(this@ErrorParsingCall, Response.success(HttpResult.ApiError(message, code)))
                        }
                    }
                }?: callback.onResponse(this@ErrorParsingCall, Response.success(HttpResult.NullResult()))
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ErrorParsingCall, Response.success(HttpResult.NetworkError(t)))
                call.cancel()
            }
    }
    )

    override fun clone(): Call<HttpResult<T>> = ErrorParsingCall(delegate.clone())
    override fun execute(): Response<HttpResult<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

}
