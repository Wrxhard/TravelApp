package com.wrxhard.ftravel.data.remote.location_api

import com.wrxhard.ftravel.model.base_model.Location
import com.wrxhard.ftravel.util.Resource
import javax.inject.Inject

//TODO: Implement LocationRepo
class LocationRepoImp @Inject constructor(
    private val locationService: LocationService
) : LocationRepo {
    override suspend fun getLocations(): Resource<List<Location>> {
        return try {
            val respone = locationService.getLocations()
            val res = respone.body()
            if (respone.isSuccessful && res != null) {
                Resource.Success(res)
            } else {
                Resource.Error(
                    respone.message()
                        ?: "Get All Location Failed An error occured"
                )
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Get All Location Failed An error occured")
        }
    }

    override suspend fun getTopLocations(): Resource<List<Location>> {
        return try {
            val respone = locationService.getTopLocations()
            val res = respone.body()
            if (respone.isSuccessful && res != null) {
                Resource.Success(res)
            } else {
                Resource.Error(
                    respone.message()
                        ?: "Get Top Location Failed An error occured"
                )
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Get Top Location Failed An error occured")
        }
    }
}