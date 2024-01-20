package com.wrxhard.ftravel.data.local.local_repo.food_repo

import com.wrxhard.ftravel.model.base_model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepo {
    suspend fun insertFood(movie: Food)

    fun getLocalFoods(): Flow<List<Food>>

    suspend fun getLocalFood(id:String): Food?

    suspend fun deleteAll()
}