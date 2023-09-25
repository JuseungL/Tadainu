package com.mp.tadainu.api.auth.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("usr_id") val usr_id: String,
    @SerializedName("usr_pwd") val usr_pwd: String
)
