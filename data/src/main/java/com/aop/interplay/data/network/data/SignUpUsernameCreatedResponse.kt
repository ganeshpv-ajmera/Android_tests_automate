package com.aop.interplay.data.network.data

import com.google.gson.annotations.SerializedName

data class SignUpUsernameCreatedResponse(
    @SerializedName("id") var id:String = "",
    @SerializedName("roles") var roles:List<String> = emptyList(),
    @SerializedName("firstName") var firstName:String = "",
    @SerializedName("lastName") var lastName:String = "",
    @SerializedName("email") var email:String = "",
    @SerializedName("username") var username:String = "",
    @SerializedName("dob") var dob:String = "",
    @SerializedName("phone") var phone:String = "",
    @SerializedName("oktaUser") var oktaUser:String = "",
    @SerializedName("createdDate") var createdDate:Long = 0L,
    @SerializedName("UpdatedDate") var UpdatedDate:Long = 0L,
    @SerializedName("accessToken") var accessToken:String = "",
    @SerializedName("refreshToken") var refreshToken:String = ""
    )
