package com.wrxhard.ftravel.data.remote.location_api

import com.wrxhard.ftravel.model.base_model.Location
import com.wrxhard.ftravel.util.Resource

// TODO: Implement LocationRepo interface
interface LocationRepo {
    suspend fun getLocations(): Resource<List<Location>>
    suspend fun getTopLocations(): Resource<List<Location>>
}