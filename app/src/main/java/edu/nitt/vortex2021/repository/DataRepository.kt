package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.DataApiService
import edu.nitt.vortex2021.model.College
import edu.nitt.vortex2021.model.CollegeResponse
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val dataApiService: DataApiService) {
    suspend fun fetchColleges(): Response<CollegeResponse> {
        return dataApiService.getColleges()
    }
}