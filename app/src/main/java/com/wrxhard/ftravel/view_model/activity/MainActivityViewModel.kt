package com.wrxhard.ftravel.view_model.activity

import androidx.lifecycle.ViewModel
import com.wrxhard.ftravel.data.local.local_repo.LocalRepo
import com.wrxhard.ftravel.data.remote.RemoteRepo
import com.wrxhard.ftravel.data.remote.auth_api.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel

class MainActivityViewModel constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo
): ViewModel(){



}