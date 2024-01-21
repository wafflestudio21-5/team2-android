package com.wafflestudio.bunnybunny.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.call_adapter.ErrorParsingCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addNetworkInterceptor(interceptor)
            .addInterceptor{ chain ->
                val newRequest = chain.request().
                newBuilder().build()
                chain.proceed(newRequest)
            }
            .build()
    }
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("http://43.202.236.170:8080/")
            .client(okHttpClient).addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ErrorParsingCallAdapterFactory())
            .build()
    }
    @Provides
    fun bunnyApi(
        retrofit: Retrofit,
    ): BunnyApi {
        return retrofit.create(BunnyApi::class.java)
    }
}