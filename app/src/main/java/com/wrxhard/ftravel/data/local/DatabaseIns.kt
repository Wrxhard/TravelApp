package com.wrxhard.ftravel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wrxhard.ftravel.data.local.dao.FoodDAO
import com.wrxhard.ftravel.data.local.dao.LocationDAO
import com.wrxhard.ftravel.model.base_model.list_item.Food
import com.wrxhard.ftravel.model.base_model.list_item.Location
import com.wrxhard.ftravel.util.Converter

@Database(
    entities = [Food::class, Location::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class DatabaseIns: RoomDatabase() {
    abstract fun getDatabaseFoodDAO(): FoodDAO
    abstract fun getDatabaseLocationDAO(): LocationDAO
}