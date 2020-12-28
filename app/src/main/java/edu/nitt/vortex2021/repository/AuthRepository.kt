package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.AuthApiService
import edu.nitt.vortex2021.model.*
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {
    suspend fun sendRegisterRequest(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authApiService.getRegisterResponse(registerRequest)
    }

    suspend fun sendLoginRequest(loginRequest: LoginRequest): Response<LoginResponse> {
        return authApiService.getLoginResponse(loginRequest)
    }

    suspend fun resendVerificationToken(): Response<ResendVerificationTokenResponse> {
        return authApiService.resendVerificationToken()
    }
}