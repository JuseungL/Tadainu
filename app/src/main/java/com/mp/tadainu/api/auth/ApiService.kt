package com.mp.tadainu.api.auth

import com.mp.tadainu.api.auth.request.CheckIdRequest
import com.mp.tadainu.api.auth.request.CheckNicknameRequest
import com.mp.tadainu.api.auth.request.SaveUserInfoRequest
import com.mp.tadainu.api.auth.response.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/auth/check-id")
    fun checkId(@Body request: CheckIdRequest): Call<ApiResponse>

    @POST("/auth/check-nickname")
    fun checkNickname(@Body request: CheckNicknameRequest): Call<ApiResponse>

    @POST("/auth/saveinfo") // Replace with the actual API endpoint
    fun saveUserInfo(@Body request: SaveUserInfoRequest): Call<ApiResponse> // ApiResponse is the response model from your server

}
/***
 * 이 코드는 ApiService라는 Retrofit API 서비스 인터페이스를 정의합니다.
 * @POST("/auth/check-id")는 "/auth/check-id" 엔드포인트로의 POST 요청임을 나타냅니다.
 * @Query("usr_id") userId: String는 userId 매개변수를 요청의 쿼리 매개변수로 전달할 것임을 지정합니다.
 * Call<CheckIdResponse>는 HTTP 호출을 나타내는 Retrofit의 방식으로, CheckIdResponse 객체를 반환합니다.
 */