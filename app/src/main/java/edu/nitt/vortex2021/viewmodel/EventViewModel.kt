package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.model.EventListResponse
import edu.nitt.vortex2021.repository.EventRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class EventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel(){
    val eventListResponse = MutableLiveData<Resource<EventListResponse>>()

    fun fetchEventList(){
        viewModelScope.launch {
            eventListResponse.postValue(Resource.Loading())
            try {
                val response = repository.fetchEventList()
                eventListResponse.postValue(handleEventListResponse(response))
            } catch (e: Exception) {
                eventListResponse.postValue(Resource.Error("No Internet"))
            }
        }
    }

    private fun handleEventListResponse(response: Response<EventListResponse>): Resource<EventListResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        val jsonObject = JSONObject(response.errorBody()!!.toString())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
    }
}