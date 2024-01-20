package com.wrxhard.ftravel.data.local.local_repo.location_repo

import com.wrxhard.ftravel.data.local.dao.LocationDAO
import com.wrxhard.ftravel.model.base_model.Location
import kotlinx.coroutines.flow.Flow

class LocationRepoImp(
    private val dao: LocationDAO
): LocationRepo {
    override suspend fun insertLocation(movie: Location) {
        dao.insertLocation(movie)
    }

    override fun getLocalLocations(): Flow<List<Location>> {
        return dao.getLocalLocations()
    }

    override suspend fun getLocalLocation(id: String): Location? {
        return dao.getLocalLocation(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}