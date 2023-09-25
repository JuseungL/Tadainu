package com.mp.tadainu.api.auth.request

import com.google.gson.annotations.SerializedName

data class SaveUserInfoRequest(
    @SerializedName("usr_id") val usr_id: String,
    @SerializedName("usr_pwd") val usr_pwd: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("name") val name: String,
    @SerializedName("phonenumber") val phonenumber: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("birth") val birth: String
)