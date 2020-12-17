package edu.nitt.vortex21.api


import com.google.gson.GsonBuilder
import edu.nitt.vortex21.helpers.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
    .create()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(Constants.API_BASE_URL)
    .build()



object VortexApi {
    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val storyApiService: StoryApiService by lazy {
        retrofit.create(StoryApiService::class.java)
    }
}