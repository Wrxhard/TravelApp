package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.UserAuthResp
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/loginonmobile")
    suspend fun login(@Body body: RequestBody): Response<UserAuthResp>
    @POST("/register")
    suspend fun register(@Body body: RequestBody):Response<UserAuthResp>
    @POST("/loginWithGoogle")
    suspend fun loginGoogle(@Body body: RequestBody):Response<UserAuthResp>
}