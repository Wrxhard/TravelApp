package com.wrxhard.ftravel.data.remote.food_api

import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.util.Resource

// TODO: Implement FoodRepo interface
interface FoodRepo {
    suspend fun getFoods(): Resource<List<Food>>
    suspend fun getFavoriteFoods(): Resource<List<Food>>
}