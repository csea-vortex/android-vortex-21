package edu.nitt.vortex21.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex21.helpers.Resource
import edu.nitt.vortex21.model.LoginRequest
import edu.nitt.vortex21.model.LoginResponse
import edu.nitt.vortex21.model.RegisterRequest
import edu.nitt.vortex21.model.RegisterResponse
import edu.nitt.vortex21.repository.AuthRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class AuthViewModel: ViewModel() {
    private val repository = AuthRepository()
    val registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val loginResponse = MutableLiveData<Resource<LoginResponse>>()

    fun sendRegisterRequest(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerResponse.postValue(Resource.Loading())
            val response = repository.sendRegisterRequest(registerRequest)
            registerResponse.postValue(handleRegisterResponse(response))
        }
    }

    private fun handleRegisterResponse(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { registerResponse ->
                return Resource.Success(registerResponse)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        var message = jsonObject.getString("message")
        if (message.startsWith("E1100")) {
            message = "A user with given e-mail already registered"
        }
        return Resource.Error(message)
    }

    fun sendLoginRequest(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginResponse.postValue(Resource.Loading())
            val response = repository.sendLoginRequest(loginRequest)
            loginResponse.postValue(handleLoginResponse(response))
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                return Resource.Success(loginResponse)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
    }
}