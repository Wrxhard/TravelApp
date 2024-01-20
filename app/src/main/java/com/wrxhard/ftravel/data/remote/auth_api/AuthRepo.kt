package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.UserAuthResp
import com.wrxhard.ftravel.util.Resource
import okhttp3.RequestBody
import retrofit2.http.Body

interface AuthRepo {
    suspend fun login(@Body body: RequestBody): Resource<UserAuthResp>
    suspend fun register(@Body body: RequestBody): Resource<UserAuthResp>
    suspend fun loginGoogle(@Body body: RequestBody): Resource<UserAuthResp>
}