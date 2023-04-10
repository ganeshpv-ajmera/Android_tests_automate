package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpMobileNumberBody(
    @SerializedName("phoneNumber") val phoneNumber: String
)
