package com.aop.interplay.network.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val responseCode:Int? = null,
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null, responseCode:Int) : NetworkResult<T>(data, message,responseCode)
    class Exception<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
}