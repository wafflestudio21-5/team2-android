package com.wafflestudio.bunnybunny.lib.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityPostContent(
    @Json(name = "community") val community: Community,
    @Json(name = "isLiked") var isLike: Boolean,
    @Json(name = "nickname") val authorName: String,
    @Json(name = "profileImg") val profileImg: String?,
    @Json(name = "areaInfo") val areaName: String,

    )
@JsonClass(generateAdapter = true)
data class Community(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "areaId") val areaId: Long,
    @Json(name = "authorId") val authorId: Long,
    @Json(name = "images") val images: List<String>,
    @Json(name = "viewCnt") val viewCnt: Int,
    @Json(name = "likeCnt") var likeCnt: Int,
    @Json(name = "chatCnt") val chatCnt: Int,
    @Json(name = "createdAt") val createdAt: Long,
)
@JsonClass(generateAdapter = true)
data class ChildComment(
    @Json(name = "id") val id: Long,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "comment") val comment: String,
    @Json(name = "imgUrl") val imgUrl: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "likeCnt") val likeCnt: Int,
    @Json(name = "isLiked") val isLiked: Boolean,
    @Json(name = "images") val images: List<String>,
    )

@JsonClass(generateAdapter = true)
data class Comment(
    @Json(name = "id") val id: Long,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "comment") val comment: String,
    @Json(name = "imgUrl") val imgUrl: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "likeCnt") val likeCnt: Int,
    @Json(name = "isLiked") val isLiked: Boolean,
    @Json(name = "childComments") val childComments: List<ChildComment>,
    @Json(name = "images") val images: List<String>,
)

@JsonClass(generateAdapter = true)
data class PostCommentParams(
    @Json(name = "comment") val comment: String,
    @Json(name = "imgUrl") val imgUrl: String = "",
    @Json(name = "parentId") val parentId: Long?,
    @Json(name = "images") val images: List<String> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class PutCommentParams(
    @Json(name = "comment") val comment: String,
    @Json(name = "imgUrl") val imgUrl: String = "",
    @Json(name = "images") val images: List<String> = emptyList(),
)


