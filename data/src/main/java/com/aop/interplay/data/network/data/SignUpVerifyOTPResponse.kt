package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpVerifyOTPResponse(
    @SerializedName("id")  val id: Int,
    @SerializedName("accessToken")  val accessToken: String,
    @SerializedName("refreshToken")  val refreshToken: String,
)
