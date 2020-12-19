package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.UserApiService
import edu.nitt.vortex2021.model.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun fetchUserDetails(): Response<UserResponse> {
        return userApiService.getUserDetails()
    }
}
