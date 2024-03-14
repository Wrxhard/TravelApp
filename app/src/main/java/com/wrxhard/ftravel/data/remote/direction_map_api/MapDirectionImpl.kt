package com.wrxhard.ftravel.data.remote.direction_map_api

import com.wrxhard.ftravel.model.google.DirectionResponseModel
import com.wrxhard.ftravel.util.Resource
import javax.inject.Inject

class MapDirectionImpl @Inject constructor(
    private val directionMapApi: MapDirectionService
): MapDirectionRepo {
    override suspend fun getDirection(
        origin: String,
        destination: String,
        mode: String,
        apiKey: String
    ): Resource<DirectionResponseModel> {
        return try {
            val responseModel = directionMapApi.getDirection(origin, destination, mode, apiKey);
            val res = responseModel.body();
            if( responseModel.isSuccessful && res!== null ) {
                Resource.Success(res);
            }else {
                Resource.Error(responseModel.message() ?:"An error occured")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

}