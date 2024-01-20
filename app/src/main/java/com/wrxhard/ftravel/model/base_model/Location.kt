package com.wrxhard.ftravel.model.base_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    "Location_table"
)
data class Location(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
)
