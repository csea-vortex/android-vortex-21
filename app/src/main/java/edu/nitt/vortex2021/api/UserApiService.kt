package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("user/details")
    suspend fun getUserDetails(): Response<UserResponse>
}