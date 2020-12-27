package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.UserResponse
import edu.nitt.vortex2021.repository.UserRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    public val userDetailsResponse = MutableLiveData<Resource<UserResponse>>()

    fun sendUserDetailsRequest() {
        viewModelScope.launch {
            userDetailsResponse.postValue(Resource.Loading())
            try {
                val response = repository.fetchUserDetails()
                userDetailsResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                userDetailsResponse.postValue(Resource.Error("No internet"))
            }
        }
    }
}