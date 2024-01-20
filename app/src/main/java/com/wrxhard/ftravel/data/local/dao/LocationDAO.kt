package com.wrxhard.ftravel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wrxhard.ftravel.model.base_model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(food: Location)

    @Query("SELECT * FROM Location_table")
    fun getLocalLocations(): Flow<List<Location>>

    @Query("SELECT * FROM Location_table WHERE id = :id")
    suspend fun getLocalLocation(id:String): Location?

    @Query("DELETE FROM Location_table")
    suspend fun deleteAll()

}