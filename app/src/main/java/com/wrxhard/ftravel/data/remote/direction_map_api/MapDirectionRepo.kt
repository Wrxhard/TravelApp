package com.wrxhard.ftravel.data.remote.direction_map_api

import com.wrxhard.ftravel.model.google.DirectionResponseModel
import com.wrxhard.ftravel.util.Resource
import retrofit2.http.Query

interface MapDirectionRepo {
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String,
        @Query("key") apiKey: String
    ): Resource<DirectionResponseModel>
}