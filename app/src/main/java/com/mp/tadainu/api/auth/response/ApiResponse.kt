package com.mp.tadainu.api.auth.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: List<Data>?
)
data class Data(
    val id: Int
)