package com.wafflestudio.bunnybunny.di

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("NetworkOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addNetworkInterceptor(interceptor)
            .addInterceptor{ chain ->
                val newRequest = chain.request().
                newBuilder()
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    @Provides
    fun provideRetrofit(
        @Named("NetworkOkHttpClient") okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder().baseUrl("http://banibani.shop/")
            .client(okHttpClient).addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    @Provides
    fun bunnyApi(
        retrofit: Retrofit,
    ): BunnyApi {
        return retrofit.create(BunnyApi::class.java)
    }
}