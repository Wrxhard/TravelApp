package com.wrxhard.ftravel.data.local.location_repo

import com.wrxhard.ftravel.model.base_model.list_item.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    suspend fun insertLocation(movie: Location)

    fun getLocalLocations(): Flow<List<Location>>

    suspend fun getLocalLocation(id:String): Location?

    suspend fun deleteAll()
}