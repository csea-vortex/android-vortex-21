package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun getRegisterResponse(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun getLoginResponse(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun getLogoutResponse(): Response<LoginResponse>
}
