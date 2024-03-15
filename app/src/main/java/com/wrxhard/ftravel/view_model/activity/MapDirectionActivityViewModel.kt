package com.wrxhard.ftravel.view_model.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrxhard.ftravel.data.remote.RemoteRepo
import com.wrxhard.ftravel.model.google.DirectionResponseModel
import com.wrxhard.ftravel.model.google.Location
import com.wrxhard.ftravel.util.DispatcherProvider
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapDirectionActivityViewModel @Inject constructor(
    private  val remoteRepo: RemoteRepo,
    private val dispatcher: DispatcherProvider
    ): ViewModel() {

    private val _direction = MutableStateFlow<Event<DirectionResponseModel>>(Event.Empty)
    val directionEvent: StateFlow<Event<DirectionResponseModel>> = _direction

    fun getDirection(origin: Location, destination: Location, mode: String, key: String) {
        viewModelScope.launch(dispatcher.io) {
            val originStr = "${origin.latitude},${origin.longitude}"
            val destinationStr = "${destination.latitude},${destination.longitude}"
            val res = remoteRepo.mapDirectionRepo.getDirection(originStr, destinationStr, mode, key)
            _direction.value = Event.Loading
            _direction.value= Event.Loading
            when (res) {
                is Resource.Success -> {
                    if (res.data != null) {
                        _direction.value = Event.Success(res.data)
                    } else {
                        _direction.value = Event.Failure("No response body")
                    }
                }
                is Resource.Error -> {
                    _direction.value = Event.Failure("${res.message}")
                }
            }
        }
    }

    }

