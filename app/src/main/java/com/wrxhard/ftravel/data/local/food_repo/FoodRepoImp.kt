package com.wrxhard.ftravel.data.local.food_repo

import com.wrxhard.ftravel.data.local.dao.FoodDAO
import com.wrxhard.ftravel.model.base_model.Food
import kotlinx.coroutines.flow.Flow

class FoodRepoImp(
    private val dao: FoodDAO
): FoodRepo {
    override suspend fun insertFood(movie: Food) {
        dao.insertFood(movie)
    }

    override fun getLocalFoods(): Flow<List<Food>> {
        return dao.getLocalFoods()
    }

    override suspend fun getLocalFood(id: String): Food? {
        return dao.getLocalFood(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}