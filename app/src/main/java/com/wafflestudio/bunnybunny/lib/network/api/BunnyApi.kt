package com.wafflestudio.bunnybunny.lib.network.api

import com.wafflestudio.bunnybunny.data.example.AreaSearchResponse
import com.wafflestudio.bunnybunny.data.example.ChatListResponse
import com.wafflestudio.bunnybunny.data.example.CreateChatRoomRequest
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.RefAreaRequest
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SignupResponse
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.dto.AuctionInfo
import com.wafflestudio.bunnybunny.lib.network.dto.AuctionRequest
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitCommunityPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.postImagesResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
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

        @Part images: List<MultipartBody.Part>,
    ): postImagesResponse

    @POST("/posts")
    suspend fun submitPostRequest(
        @Header("Authorization") authToken:String,
        @Body request:SubmitPostRequest,
    )

    @POST("/community")
    suspend fun submitCommunityPostRequest(
        @Header("Authorization") authToken:String,
        @Body request: SubmitCommunityPostRequest,
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

    @GET("/community/{communityId}")
    suspend fun getCommunityPostContent(
        @Header("Authorization") authToken:String,
        @Path("communityId") communityId:Long,
    ) : CommunityPostContent

    @GET("/posts/search")
    suspend fun searchPostList(
        @Header("Authorization") authToken:String,
        @Query("keyword") keyword:String,
        @Query("cur") cur: Long?,
        @Query("distance") distance:Int,
        @Query("areaId") areaId: Int,
        @Query("count") count:Int?,
    ) : GoodsPostList


  
    @POST("/signup")
    suspend fun signupRequest(@Body request: SignupRequest): SignupResponse

    @GET("/signup/nick/{nickname}")
    suspend fun checkDuplicateNickname(@Path("nickname") nickName: String)

    @POST("/auth/login/{provider}")
    suspend fun socialLoginRequest(@Body request: SocialLoginRequest, @Path("provider") provider: String): SocialLoginResponse

    @POST("/signup/{provider}")
    suspend fun socialSignUpRequest(@Body request: SocialSignupRequest, @Path("provider") provider: String): SignupRequest

    @GET("/area/search")
    suspend fun areaSearch(@Query("query") query: String, @Query("cursor") cursor: Int): AreaSearchResponse

    @GET("/channels")
    suspend fun chatChannelRequest(@Header("Authorization") authToken:String): ChatListResponse

    @POST("/channels")
    suspend fun makeChatRoomRequest(@Header("Authorization") authToken:String, @Body request: CreateChatRoomRequest)

    @POST("/channels/{channelId}/pin")
    suspend fun postPinRequest(@Header("Authorization") authToken:String, @Path("channelId") channelId: Long)

    @DELETE("/channels/{channelId}/pin")
    suspend fun deletePinRequest(@Header("Authorization") authToken:String, @Path("channelId") channelId: Long)

    @GET("/posts/wish")
    suspend fun getWishList(@Header("Authorization") authToken: String): List<GoodsPostPreview>

    @GET("/user")
    suspend fun getUserInfo(@Header("Authorization") authToken: String): UserInfo

    @PUT("/user")
    suspend fun putUserInfo(@Header("Authorization") authToken: String,
                    @Body request: EditProfileRequest): UserInfo

    @GET("/area/{id}")
    suspend fun getAreaName(@Path("id") id: Int): SimpleAreaData

    @POST("/user/refArea")
    suspend fun postRefArea(@Header("Authorization") authToken: String,
                            @Body request: RefAreaRequest
    ): LoginResponse

    @DELETE("/user/refArea")
    suspend fun deleteRefArea(@Header("Authorization") authToken: String,
                              @Body request: RefAreaRequest): LoginResponse

    @GET("posts/my")
    suspend fun getMyPostList(@Header("Authorization") authToken: String): List<GoodsPostPreview>

    @GET("posts/auction/{postId}")
    suspend fun getAuctionList(@Header("Authorization") authToken: String, @Path("postId") postId: Long): List<AuctionInfo>

    @POST("posts/auction/{postId}")
    suspend fun postAuction(@Header("Authorization") authToken: String, @Path("postId") postId: Long, @Body request: AuctionRequest)
}