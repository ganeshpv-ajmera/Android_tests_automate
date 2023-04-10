package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpUsernameValidationResponse(
    @SerializedName("message") val message: String
)