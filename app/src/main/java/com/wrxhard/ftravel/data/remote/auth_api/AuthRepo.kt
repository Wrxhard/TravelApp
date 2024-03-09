package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.network_req.UserAuthRequest
import com.wrxhard.ftravel.model.base_model.network_resp.UserAuthResp
import com.wrxhard.ftravel.util.Resource
import retrofit2.http.Body

interface AuthRepo {
    suspend fun login(@Body request: UserAuthRequest) : Resource<UserAuthResp>
    suspend fun register(@Body request: UserAuthRequest): Resource<UserAuthResp>
    suspend fun loginGoogle(@Body request: UserAuthRequest): Resource<UserAuthResp>
}