package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.CollegeResponse
import edu.nitt.vortex2021.model.WorkshopResponse
import retrofit2.Response
import retrofit2.http.GET

interface DataApiService {
    @GET("college-list")
    suspend fun getColleges(): Response<CollegeResponse>

    @GET("workshop-list")
    suspend fun getWorkshops(): Response<WorkshopResponse>
}