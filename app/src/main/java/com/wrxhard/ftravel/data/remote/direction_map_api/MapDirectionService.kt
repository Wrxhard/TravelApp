package com.wrxhard.ftravel.data.remote.direction_map_api

import com.wrxhard.ftravel.model.google.DirectionResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapDirectionService {
    @GET("https://maps.googleapis.com/maps/api/directions/json?")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String,
        @Query("key") apiKey: String
    ): Response<DirectionResponseModel>
}