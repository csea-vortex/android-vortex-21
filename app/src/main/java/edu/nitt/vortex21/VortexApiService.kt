package edu.nitt.vortex21

import edu.nitt.vortex21.model.LoginRequest
import edu.nitt.vortex21.model.LoginResponse
import edu.nitt.vortex21.model.RegisterRequest
import edu.nitt.vortex21.model.RegisterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://spider.nitt.edu/vortex21/"

interface AuthApiService {
    @POST("register")
    suspend fun getRegisterResponse(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun getLoginResponse(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object VortexApi {
    val authApiService: AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
}