package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpUserBody(
    @SerializedName("username") var username: String = "",
    @SerializedName("dob") var dob:String = "",
)
