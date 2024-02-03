package com.wrxhard.ftravel.data.local

import com.wrxhard.ftravel.data.local.food_repo.FoodRepo
import com.wrxhard.ftravel.data.local.location_repo.LocationRepo
import javax.inject.Inject

class LocalRepo @Inject constructor(
   val locationRepo: LocationRepo,
   val foodRepo: FoodRepo
)