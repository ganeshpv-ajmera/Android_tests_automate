package com.aop.interplay.network.model

import com.aop.interplay.network.utils.NetworkResult
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}", response.code())
        } catch (e: Exception) {
            return throwException(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String, responseCode:Int): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage", responseCode = responseCode)

    private fun <T> throwException(exceptionMessage: String): NetworkResult<T> =
        NetworkResult.Exception(exceptionMessage)
}