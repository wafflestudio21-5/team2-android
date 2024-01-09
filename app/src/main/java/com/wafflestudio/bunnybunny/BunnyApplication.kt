package com.wafflestudio.bunnybunny

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import com.wafflestudio.bunnybunny.BuildConfig

@HiltAndroidApp
class BunnyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}