package com.aop.interplay.network.remote

import com.aop.interplay.data.network.data.*
import retrofit2.Response
import retrofit2.http.*

interface SignUpApiService {
    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36","Content-Type: application/json")
    @POST("signup/passwordless")
    suspend fun postMobileNumber(@Body mobileNumber: SignUpMobileNumberBody): Response<SignUpOTPResponse>

    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36","Content-Type: application/json")
    @POST("signup/otp")
    suspend fun verifyOtp(@Body otpBody: SignUpOTPBody): Response<SignUpVerifyOTPResponse>

    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
    @GET("users/{username}/check")
    suspend fun validateUsername(@Path("username") username: String): Response<List<SignUpUsernameValidationResponse>>
    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36","Content-Type: application/json")
    @PATCH("users/{id}")
    suspend fun setUser(@Body userBody: SignUpUserBody,@Path("id") id:Int): Response<SignUpUsernameUpdatedResponse>
}