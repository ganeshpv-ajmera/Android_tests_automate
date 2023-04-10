package com.aop.interplay.network.remote

import android.app.Application
import com.aop.interplay.data.network.HomeVideoItem
import com.aop.interplay.network.model.BaseApiResponse
import com.aop.interplay.network.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class NetworkRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val application: Application
) : BaseApiResponse() {
    suspend fun getPosts(page: Int, limit: Int): Flow<NetworkResult<HomeVideoItem>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getPosts(page = page, limit = limit) })
        }.flowOn(Dispatchers.IO)
    }
}