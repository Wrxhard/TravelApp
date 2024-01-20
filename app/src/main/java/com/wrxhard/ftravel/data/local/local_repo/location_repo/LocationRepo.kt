package com.wrxhard.ftravel.data.local.local_repo.location_repo

import com.wrxhard.ftravel.model.base_model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    suspend fun insertLocation(movie: Location)

    fun getLocalLocations(): Flow<List<Location>>

    suspend fun getLocalLocation(id:String): Location?

    suspend fun deleteAll()
}