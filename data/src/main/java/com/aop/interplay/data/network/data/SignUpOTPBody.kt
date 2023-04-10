package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpOTPBody(
    @SerializedName("otp") private val otp: String,
    @SerializedName("phoneNumber") private val phoneNumber: String
)
