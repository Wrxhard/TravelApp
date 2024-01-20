package com.wrxhard.ftravel.data.local.local_repo

import com.wrxhard.ftravel.data.local.local_repo.food_repo.FoodRepo
import com.wrxhard.ftravel.data.local.local_repo.location_repo.LocationRepo
import javax.inject.Inject

class LocalRepo @Inject constructor(
   private val locationRepo: LocationRepo,
   private val foodRepo: FoodRepo
)