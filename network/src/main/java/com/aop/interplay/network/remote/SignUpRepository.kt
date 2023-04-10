package com.aop.interplay.network.remote

import com.aop.interplay.data.network.data.*
import com.aop.interplay.network.model.BaseApiResponse
import com.aop.interplay.network.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class SignUpRepository @Inject constructor(
    private val signUpApiService: SignUpApiService
) : BaseApiResponse() {
    suspend fun getOtp(mobileBody: SignUpMobileNumberBody): Flow<NetworkResult<SignUpOTPResponse>> {
        return flow {
            emit(safeApiCall { signUpApiService.postMobileNumber(mobileBody) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun verifyOtp(body: SignUpOTPBody): Flow<NetworkResult<SignUpVerifyOTPResponse>> {
        return flow {
            emit(safeApiCall { signUpApiService.verifyOtp(body) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun validateUsername(username: String): Flow<NetworkResult<List<SignUpUsernameValidationResponse>>> {
        return flow {
            emit(safeApiCall { signUpApiService.validateUsername(username) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun setUser(
        userBody: SignUpUserBody,
        id: Int
    ): Flow<NetworkResult<SignUpUsernameUpdatedResponse>> {
        return flow {
            emit(safeApiCall { signUpApiService.setUser(userBody, id) })
        }.flowOn(Dispatchers.IO)
    }
}
