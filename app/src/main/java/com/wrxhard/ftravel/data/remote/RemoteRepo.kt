package com.wrxhard.ftravel.data.remote

import com.wrxhard.ftravel.data.local.food_repo.FoodRepo
import com.wrxhard.ftravel.data.local.location_repo.LocationRepo
import com.wrxhard.ftravel.data.remote.auth_api.AuthRepo
import javax.inject.Inject

class RemoteRepo @Inject constructor(
    val authRepo: AuthRepo,
    val foodRepo: FoodRepo,
    val locationRepo: LocationRepo
)