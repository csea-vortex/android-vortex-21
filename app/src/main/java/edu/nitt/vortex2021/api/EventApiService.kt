package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.EventListResponse
import retrofit2.Response
import retrofit2.http.GET

interface EventApiService {
    @GET("event-list")
    suspend fun getEventListResponse(): Response<EventListResponse>
}