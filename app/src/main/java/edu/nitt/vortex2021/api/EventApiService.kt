package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.EventResponse
import edu.nitt.vortex2021.model.RegisterEventRequest
import edu.nitt.vortex2021.model.RegisterEventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventApiService {
    @GET("event-list")
    suspend fun getEventListResponse(): Response<EventResponse>

    @POST("register-for-event")
    suspend fun registerEvent(@Body registerEventRequest: RegisterEventRequest): Response<RegisterEventResponse>
}