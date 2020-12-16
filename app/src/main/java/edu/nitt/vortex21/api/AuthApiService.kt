package edu.nitt.vortex21.api

import edu.nitt.vortex21.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @POST("register")
    suspend fun getRegisterResponse(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun getLoginResponse(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
