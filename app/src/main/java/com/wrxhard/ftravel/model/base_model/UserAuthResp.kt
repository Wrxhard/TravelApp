package com.wrxhard.ftravel.model.base_model

import com.google.gson.annotations.SerializedName

data class UserAuthResp(
    @SerializedName("_id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
)