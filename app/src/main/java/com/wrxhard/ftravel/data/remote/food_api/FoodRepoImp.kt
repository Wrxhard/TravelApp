package com.wrxhard.ftravel.data.remote.food_api

import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.util.Resource
import javax.inject.Inject

//TODO: Implement FoodRepo
class FoodRepoImp @Inject constructor(
    private val foodService: FoodService
): FoodRepo {
    override suspend fun getFoods(): Resource<List<Food>> {
        return try {
            val respone = foodService.getFoods()
            val res=respone.body()
            if (respone.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(respone.message() ?:"Get All Food Failed An error occured")
            }
        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "Get All Food Failed An error occured")
        }
    }

    override suspend fun getFavoriteFoods(): Resource<List<Food>> {
        return try {
            val respone = foodService.getFavoriteFoods()
            val res=respone.body()
            if (respone.isSuccessful && res!=null)
            {
                Resource.Success(res)
            }
            else
            {
                Resource.Error(respone.message() ?:"Get Favorite Food Failed An error occured")
            }
        }catch (e:Exception)
        {
            Resource.Error(e.message ?: "Get Favorite Food Failed An error occured")
        }
    }
}