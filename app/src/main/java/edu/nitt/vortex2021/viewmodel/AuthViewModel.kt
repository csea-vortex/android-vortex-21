package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.model.LoginRequest
import edu.nitt.vortex2021.model.LoginResponse
import edu.nitt.vortex2021.model.RegisterRequest
import edu.nitt.vortex2021.model.RegisterResponse
import edu.nitt.vortex2021.repository.AuthRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
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
                registerResponse.postValue(handleRegisterResponse(response))
            } catch (e: Exception) {
                registerResponse.postValue(Resource.Error("No internet"))
            }

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
            try {
                val response = repository.sendLoginRequest(loginRequest)
                loginResponse.postValue(handleLoginResponse(response))
            } catch (e: Exception) {
                loginResponse.postValue(Resource.Error("No Internet"))
            }
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