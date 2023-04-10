package com.aop.interplay.data.network

import com.google.gson.annotations.SerializedName

data class HomeVideoItem(
    @SerializedName("homeScreenPosts") val homeScreenPosts: HomeScreenPost,
    @SerializedName("success") val success: Boolean
)

data class HomeScreenPost(
    @SerializedName("hasMore") val hasMore: Boolean,
    @SerializedName("posts") val posts: List<HomePost>,
    @SerializedName("startingAfter") val startingAfter: Int
)

data class HomePost(
    @SerializedName("userId") val userId: String,
    @SerializedName("postId") val postId: String,
    @SerializedName("description") val description: String,
    @SerializedName("hashtag") val tags: List<String>?,
    @SerializedName("type") val challengeType: String?,
    @SerializedName("createdTime") val createdTime: String,
    @SerializedName("user") val user: User,
    @SerializedName("videoInfo") val videoInfo: VideoInfo
) {
    fun getDescriptionWithTags() = StringBuilder(description).append(" ")
        .append(tags?.joinToString(" ") { "#$it" }.orEmpty())
}

data class VideoInfo(
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("duration") val duration: String
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("bioImage") val bioImage: String
)
