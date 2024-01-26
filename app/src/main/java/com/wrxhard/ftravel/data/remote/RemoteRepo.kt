package com.wrxhard.ftravel.data.remote

import com.wrxhard.ftravel.data.local.local_repo.location_repo.LocationRepo
import com.wrxhard.ftravel.data.remote.auth_api.AuthRepo
import javax.inject.Inject

class RemoteRepo @Inject constructor(
    private val authRepo: AuthRepo,
    private val locationRepo: com.wrxhard.ftravel.data.remote.location_api.LocationRepo,
    private val foodRepo: com.wrxhard.ftravel.data.remote.food_api.FoodRepo
)