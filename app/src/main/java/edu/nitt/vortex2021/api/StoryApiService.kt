package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.StoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApiService {
    @GET("story/{category}")
    suspend fun getStoryByCategory(@Path("category") category: String): Response<StoryResponse>
}
