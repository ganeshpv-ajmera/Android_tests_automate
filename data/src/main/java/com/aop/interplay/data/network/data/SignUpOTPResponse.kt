package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpOTPResponse(
    @SerializedName("message") val message: String
)
