package com.wrxhard.ftravel.view_model.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrxhard.ftravel.data.remote.RemoteRepo
import com.wrxhard.ftravel.model.base_model.UserAuthResp
import com.wrxhard.ftravel.util.DispatcherProvider
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

@HiltViewModel
class AuthActivityViewModel constructor(
    private val remoteRepo: RemoteRepo,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _login = MutableStateFlow<Event<UserAuthResp>>(Event.Empty)
    val loginevent: StateFlow<Event<UserAuthResp>> = _login
    fun login(username: String, password: String) {
        //add username and password to request body
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()

        //start perform post request
        viewModelScope.launch(dispatcher.io){
            val res=remoteRepo.authRepo.login(requestBody)
            _login.value= Event.Loading
            when(res){
                is Resource.Success ->  {
                    if (res.data!=null && res.data.status)
                    {
                        _login.value = Event.Success(res.data)
                    }
                    else
                    {
                        _login.value = Event.Failure("Incorrect username or password")
                    }
                }
                is Resource.Error -> _login.value = Event.Failure("${res.message}")
            }
        }
    }



}