package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.network_req.UserAuthRequest
import com.wrxhard.ftravel.model.base_model.network_resp.UserAuthResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body request: UserAuthRequest): Response<UserAuthResp>
    @POST("api/auth/register")
    suspend fun register(@Body request: UserAuthRequest):Response<UserAuthResp>
    @POST("api/auth/login/google")
    suspend fun loginGoogle(@Body request: UserAuthRequest):Response<UserAuthResp>
}