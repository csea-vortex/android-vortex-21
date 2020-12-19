package edu.nitt.vortex21.api

import edu.nitt.vortex21.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("user/details")
    suspend fun getUserDetails(): Response<UserResponse>
}