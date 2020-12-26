package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.EventListApiService
import edu.nitt.vortex2021.model.EventListResponse
import retrofit2.Response
import javax.inject.Inject

class EventListRepository @Inject constructor(private val eventListApiService: EventListApiService)  {
    suspend fun fetchEventList(): Response<EventListResponse>{
        return eventListApiService.getEventListResponse()
    }
}