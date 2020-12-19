package edu.nitt.vortex21.repository

import edu.nitt.vortex21.api.UserApiService
import edu.nitt.vortex21.model.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun fetchUserDetails(): Response<UserResponse> {
        return userApiService.getUserDetails()
    }
}
