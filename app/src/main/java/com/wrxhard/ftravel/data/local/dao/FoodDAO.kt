package com.wrxhard.ftravel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wrxhard.ftravel.model.base_model.list_item.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Query("SELECT * FROM Food_table")
    fun getLocalFoods(): Flow<List<Food>>

    @Query("SELECT * FROM Food_table WHERE id = :id")
    suspend fun getLocalFood(id:String): Food?

    @Query("DELETE FROM Food_table")
    suspend fun deleteAll()

}