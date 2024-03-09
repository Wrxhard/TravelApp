package com.wrxhard.ftravel.model.base_model.network_req

import com.google.gson.annotations.SerializedName

data class UserAuthRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
