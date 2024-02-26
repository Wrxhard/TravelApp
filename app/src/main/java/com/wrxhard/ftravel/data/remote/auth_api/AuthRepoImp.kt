package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.UserAuthRequest
import com.wrxhard.ftravel.model.base_model.UserAuthResp
import com.wrxhard.ftravel.util.Resource
import javax.inject.Inject

class AuthRepoImp @Inject constructor(
    private val authApi: AuthService
): AuthRepo {
    override suspend fun login(request: UserAuthRequest): Resource<UserAuthResp> {
        return try {
            val response = authApi.login(request)
            val res=response.body()
            if (response.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(response.message() ?:"An error occured")
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun register(request: UserAuthRequest): Resource<UserAuthResp> {
        return try {
            val response = authApi.register(request)
            val res=response.body()
            if (response.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(response.message())
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun loginGoogle(request: UserAuthRequest): Resource<UserAuthResp> {
        return try {
            val response = authApi.loginGoogle(request)
            val res=response.body()
            if (response.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(response.message())
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

}