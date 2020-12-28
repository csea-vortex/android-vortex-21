package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.EventResponse
import edu.nitt.vortex2021.model.RegisterEventResponse
import edu.nitt.vortex2021.repository.EventRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {
    val eventListResponse = MutableLiveData<Resource<EventResponse>>()
    val eventRegisterResponse = MutableLiveData<Resource<RegisterEventResponse>>()

    fun fetchEventList() {
        viewModelScope.launch {
            eventListResponse.postValue(Resource.Loading())
            try {
                val response = repository.fetchEventList()
                eventListResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                eventListResponse.postValue(Resource.Error("No Internet"))
            }
        }
    }

    fun sendEventRegistrationRequest(eventId: String) {
        viewModelScope.launch {
            eventRegisterResponse.postValue(Resource.Loading())
            try {
                val response = repository.sendEventRegistrationRequest(eventId)
                eventRegisterResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                eventRegisterResponse.postValue(Resource.Error("No Internet"))
            }
        }
    }
}