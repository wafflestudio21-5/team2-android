package com.wafflestudio.bunnybunny.lib.network

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManager
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun UserApiClient.Companion.loginWithKakao(context: Context): OAuthToken {
    return if (instance.isKakaoTalkLoginAvailable(context)) {
        try {
            UserApiClient.loginWithKakaoTalk(context)
        } catch (error: Throwable) {
            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
            // 그냥 에러를 올린다.
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error

            // 그렇지 않다면, 카카오 계정 로그인을 시도한다.
            UserApiClient.loginWithKakaoAccount(context)
        }
    } else {
        UserApiClient.loginWithKakaoAccount(context)
    }
}

/**
 * 카카오톡으로 로그인 시도
 */
suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken {
    return suspendCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token)
            } else {
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }
}

/**
 * 카카오 계정으로 로그인 시도
 */
suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken {
    return suspendCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token)
            } else {
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }
}

suspend fun getKakaoOAuthToken(context: Context): OAuthToken? {
    try {
            // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
            val oAuthToken = UserApiClient.loginWithKakao(context)
            Log.i("getKakaoOAuthToken", "${oAuthToken}")
            return oAuthToken
        } catch (error: Throwable) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                Log.i("getKakaoOAuthToken", "사용자가 명시적으로 취소")
                return null
            } else {
                Log.e("getKakaoOAuthToken", "인증 에러 발생", error)
                return null
            }
    }
}

suspend fun checkUserData(context: Context) {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e("checkUserData", "사용자 정보 요청 실패 $error")
        } else if (user != null) {
            Log.e("checkUserData", "사용자 정보 요청 성공 : $user")
            Log.d("checkUserData", "${user.kakaoAccount?.profile?.nickname}")
            Log.d("checkUserData", "${user.id}")
        }
    }
}

suspend fun loginWithTokenVerification(context: Context) {
    if (AuthApiClient.instance.hasToken()) {
        UserApiClient.instance.accessTokenInfo { _, error ->
            if (error != null) {
                if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                    //로그인 필요 관련 코드

                }
                else {
                    //기타 에러 관련 코드
                }
            }
            else {
                //토큰 유효성 체크 성공(필요 시 토큰 갱신됨) -- 일단 토큰 인포 체크 관련
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e("토큰 인포 체크", "토큰 정보 보기 실패", error)
                    }
                    else if (tokenInfo != null) {
                        Log.i("토큰 인포 체크", "토큰 정보 보기 성공" +
                                "\n토큰인포: ${tokenInfo}" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초")
                }}
                val token = TokenManager.instance.getToken() // 토큰 매니저가 idToken을 저장안하나?
                Log.d("kakaocheck", "${token}") // 여기에서는 idToken 안 나옴...
            }
        }
    }
    else {
        //로그인 필요
    }
}
