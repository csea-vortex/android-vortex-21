package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.LoginRequest
import edu.nitt.vortex2021.model.LoginResponse
import edu.nitt.vortex2021.model.RegisterRequest
import edu.nitt.vortex2021.model.RegisterResponse
import edu.nitt.vortex2021.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val loginResponse = MutableLiveData<Resource<LoginResponse>>()

    fun sendRegisterRequest(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerResponse.postValue(Resource.Loading())
            try {
                val response = repository.sendRegisterRequest(registerRequest)
                registerResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                registerResponse.postValue(Resource.Error("No internet"))
            }

        }
    }

    fun sendLoginRequest(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginResponse.postValue(Resource.Loading())
            try {
                val response = repository.sendLoginRequest(loginRequest)
                loginResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                loginResponse.postValue(Resource.Error("No Internet"))
            }
        }
    }
}