package com.wrxhard.ftravel.model.base_model.list_item

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
): Serializable
