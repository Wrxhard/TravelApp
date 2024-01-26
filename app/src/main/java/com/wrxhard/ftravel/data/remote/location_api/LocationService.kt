package com.wrxhard.ftravel.data.remote.location_api

import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.model.base_model.Location
import retrofit2.Response
import retrofit2.http.GET

//TODO: Implement LocationService
interface LocationService {
    @GET("/locations")
    suspend fun getLocations(): Response<List<Location>>
    @GET("/topLocations")
    suspend fun getTopLocations(): Response<List<Location>>
}