package edu.nitt.vortex21.repository

import edu.nitt.vortex21.api.VortexApi
import edu.nitt.vortex21.model.LoginRequest
import edu.nitt.vortex21.model.LoginResponse
import edu.nitt.vortex21.model.RegisterRequest
import edu.nitt.vortex21.model.RegisterResponse
import retrofit2.Response

class AuthRepository {
    suspend fun sendRegisterRequest(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return VortexApi.authApiService.getRegisterResponse(registerRequest)
    }

    suspend fun sendLoginRequest(loginRequest: LoginRequest): Response<LoginResponse> {
        return VortexApi.authApiService.getLoginResponse(loginRequest)
    }
}