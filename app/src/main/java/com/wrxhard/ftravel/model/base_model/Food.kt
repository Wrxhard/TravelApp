package com.wrxhard.ftravel.model.base_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    "Food_table"
)
data class Food(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("locations")
    val locations: List<Location>
)