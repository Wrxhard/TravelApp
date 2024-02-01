package com.wrxhard.ftravel.util

import androidx.room.TypeConverter
import com.wrxhard.ftravel.model.base_model.Location

class Converter {
    @TypeConverter
    fun locations(list:List<Location>):String{
        var res=""
        list.forEach {
            res+=it.id + ","
            res+=it.image_url+","
            res+=it.name+","
            res+=it.address+","
        }
        if (res.endsWith(","))
        {
            res.dropLast(1)
        }
        return res
    }
    @TypeConverter
    fun toLocation(locations: String):List<Location>{
        val list= mutableListOf<Location>()
        locations.split(",").forEach {
            val location = it.split(",")
            list.add(Location(location[0],location[1],location[2],location[3]))
        }
        return list
    }
}