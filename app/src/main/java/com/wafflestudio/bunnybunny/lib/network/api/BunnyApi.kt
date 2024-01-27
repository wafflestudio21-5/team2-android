package com.wafflestudio.bunnybunny.lib.network.api

import com.wafflestudio.bunnybunny.data.example.AreaSearchResponse
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SignupResponse
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostList
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.postImagesResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BunnyApi {

    @POST("/auth/login")
    suspend fun loginRequest(@Body request: LoginRequest) : LoginResponse

    @GET("/posts")
    suspend fun getGoodsPostList(
        @Header("Authorization") authToken:String,
        @Query("cur") cur: Long?,
        @Query("seed") seed: Int?,
        @Query("distance") distance:Int,
        @Query("areaId") areaId: Int,
        @Query("count") count:Int?,
        ) : GoodsPostList

    @GET("/posts/{post_id}")
    suspend fun getGoodsPostContent(
        @Header("Authorization") authToken:String,
        @Path("post_id") postId:Long,
    ) : GoodsPostContent

    @Multipart
    @POST("/image/upload")
    suspend fun postImages(
        @Part multipartFiles: List<MultipartBody.Part>,
    ): postImagesResponse

    @POST("/posts")
    suspend fun submitPostRequest(
        @Header("Authorization") authToken:String,
        @Body request:SubmitPostRequest,
    )


    @POST("/posts/wish/{post_id}")
    suspend fun wishToggle(
        @Header("Authorization") authToken:String,
        @Path("post_id") postId: Long,
        @Query("enable") enable:Boolean,
    )

    @GET("/community")
    suspend fun getCommunityPostList(
        @Header("Authorization") authToken:String,
        @Query("cur") cur: Long?,
        @Query("seed") seed: Int?,
        @Query("distance") distance:Int,
        @Query("areaId") areaId: Int,
        @Query("count") count:Int?,
    ) : CommunityPostList
  
    @POST("/signup")
    suspend fun signupRequest(@Body request: SignupRequest): SignupResponse

    @POST("/auth/login/{provider}")
    suspend fun socialLoginRequest(@Body request: SocialLoginRequest, @Path("provider") provider: String): SocialLoginResponse

    @POST("/signup/{provider}")
    suspend fun socialSignUpRequest(@Body request: SocialSignupRequest, @Path("provider") provider: String): SignupRequest

    @GET("/area/search")
    suspend fun areaSearch(@Query("query") query: String, @Query("cursor") cursor: Int): AreaSearchResponse

}