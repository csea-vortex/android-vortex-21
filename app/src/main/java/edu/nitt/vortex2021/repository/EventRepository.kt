package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.EventApiService
import edu.nitt.vortex2021.model.EventResponse
import edu.nitt.vortex2021.model.RegisterEventRequest
import edu.nitt.vortex2021.model.RegisterEventResponse
import retrofit2.Response
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventApiService: EventApiService) {
    suspend fun fetchEventList(): Response<EventResponse> {
        return eventApiService.getEventListResponse()
    }

    suspend fun sendEventRegistrationRequest(eventId: String): Response<RegisterEventResponse> {
        val request = RegisterEventRequest(eventId)
        return eventApiService.registerEvent(request)
    }
}