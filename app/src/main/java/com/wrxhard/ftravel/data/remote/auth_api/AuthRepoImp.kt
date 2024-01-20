package com.wrxhard.ftravel.data.remote.auth_api

import com.wrxhard.ftravel.model.base_model.UserAuthResp
import com.wrxhard.ftravel.util.Resource
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepoImp @Inject constructor(
    private val authApi: AuthService
): AuthRepo {
    override suspend fun login(body: RequestBody): Resource<UserAuthResp> {
        return try {
            val respone = authApi.login(body)
            val res=respone.body()
            if (respone.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(respone.message() ?:"An error occured")
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun register(body: RequestBody): Resource<UserAuthResp> {
        return try {
            val respone = authApi.register(body)
            val res=respone.body()
            if (respone.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(respone.message())
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun loginGoogle(body: RequestBody): Resource<UserAuthResp> {
        return try {
            val respone = authApi.loginGoogle(body)
            val res=respone.body()
            if (respone.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(respone.message())
            }

        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "An error occured")
        }
    }

}