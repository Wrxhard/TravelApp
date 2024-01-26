package com.wrxhard.ftravel.data.remote.food_api

import com.wrxhard.ftravel.model.base_model.Food
import retrofit2.Response
import retrofit2.http.GET

//TODO: Implement FoodService
interface FoodService {
    @GET("/foods")
    suspend fun getFoods(): Response<List<Food>>
    @GET("/favoriteFoods")
    suspend fun getFavoriteFoods(): Response<List<Food>>

}