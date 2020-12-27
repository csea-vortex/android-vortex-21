package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.EventApiService
import edu.nitt.vortex2021.model.EventListResponse
import retrofit2.Response
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventApiService: EventApiService)  {
    suspend fun fetchEventList(): Response<EventListResponse>{
        return eventApiService.getEventListResponse()
    }
}