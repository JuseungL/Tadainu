package com.mp.tadainu.api.auth.request

import com.google.gson.annotations.SerializedName

data class CheckNicknameRequest(
    @SerializedName("nickname") val nickname: String
)
