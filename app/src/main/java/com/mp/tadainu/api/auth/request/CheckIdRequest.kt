package com.mp.tadainu.api.auth.request

import com.google.gson.annotations.SerializedName

data class CheckIdRequest(
    @SerializedName("usr_id") val usr_id: String
)
