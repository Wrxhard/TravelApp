package com.wrxhard.ftravel.view_model.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrxhard.ftravel.data.remote.RemoteRepo
import com.wrxhard.ftravel.model.base_model.network_req.UserAuthRequest
import com.wrxhard.ftravel.model.base_model.network_resp.UserAuthResp
import com.wrxhard.ftravel.util.DispatcherProvider
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthActivityViewModel @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _login = MutableStateFlow<Event<UserAuthResp>>(Event.Empty)
    val loginevent: StateFlow<Event<UserAuthResp>> = _login
    private val _register = MutableStateFlow<Event<UserAuthResp>>(Event.Empty)
    val registerevent: StateFlow<Event<UserAuthResp>> = _register
    fun login(username: String, password: String) {
        //add username and password to request body
        val requestBody = UserAuthRequest(username, password)

        //start perform post request
        viewModelScope.launch(dispatcher.io){
            val res=remoteRepo.authRepo.login(requestBody)
            _login.value= Event.Loading
            when(res){
                is Resource.Success ->  {
                    if (res.data!=null)
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
    fun register(username: String, password: String) {
        //add username and password to request body
        val requestBody = UserAuthRequest(username, password)

        //start perform post request
        viewModelScope.launch(dispatcher.io){
            val res=remoteRepo.authRepo.register(requestBody)
            _register.value= Event.Loading
            when(res){
                is Resource.Success ->  {
                    if (res.data!=null)
                    {
                        _register.value = Event.Success(res.data)
                    }
                    else
                    {
                        _register.value = Event.Failure("Can't register with this username and password")
                    }
                }
                is Resource.Error -> _register.value = Event.Failure("${res.message}")
            }
        }
    }

    fun loginWithGoogle(username: String, password: String){
        //add username and password to request body
        val requestBody = UserAuthRequest(username, password)

        //start perform post request
        viewModelScope.launch(dispatcher.io){
            val res=remoteRepo.authRepo.loginGoogle(requestBody)
            _login.value= Event.Loading
            when(res){
                is Resource.Success ->  {
                    if (res.data!=null)
                    {
                        _login.value = Event.Success(res.data)
                    }
                    else
                    {
                        _login.value = Event.Failure("Can't login with this google account")
                    }
                }
                is Resource.Error -> _login.value = Event.Failure("${res.message}")
            }
        }
    }



}