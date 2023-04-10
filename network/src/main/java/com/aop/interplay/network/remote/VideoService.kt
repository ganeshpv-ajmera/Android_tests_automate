package com.aop.interplay.network.remote

import com.aop.interplay.data.network.HomeVideoItem
import com.aop.interplay.network.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {
    @GET(Constants.GET_HOME_VIDEOS)
    suspend fun getPosts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<HomeVideoItem>
}