package com.wrxhard.ftravel.data.local.food_repo

import com.wrxhard.ftravel.model.base_model.list_item.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepo {
    suspend fun insertFood(movie: Food)

    fun getLocalFoods(): Flow<List<Food>>

    suspend fun getLocalFood(id:String): Food?

    suspend fun deleteAll()
}