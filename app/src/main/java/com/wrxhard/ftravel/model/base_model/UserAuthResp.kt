package com.wrxhard.ftravel.model.base_model

import com.google.gson.annotations.SerializedName

data class UserAuthResp(
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id:String,
    @SerializedName("username")
    val name:String,
    @SerializedName("status")
    val status:Boolean
)



