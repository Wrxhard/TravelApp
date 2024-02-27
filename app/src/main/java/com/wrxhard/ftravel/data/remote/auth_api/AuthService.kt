package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.network_req.UserAuthRequest
import com.wrxhard.ftravel.model.base_model.network_resp.UserAuthResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: UserAuthRequest): Response<UserAuthResp>
    @POST("/auth/register")
    suspend fun register(@Body request: UserAuthRequest):Response<UserAuthResp>
    @POST("/loginWithGoogle")
    suspend fun loginGoogle(@Body request: UserAuthRequest):Response<UserAuthResp>
}