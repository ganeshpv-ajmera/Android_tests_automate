package com.aop.interplay.network.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val videoService: VideoService
){
    suspend fun getPosts(page: Int, limit: Int) = videoService.getPosts(page = page, limit = limit)
}