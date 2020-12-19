package edu.nitt.vortex21.repository

import edu.nitt.vortex21.api.StoryApiService
import edu.nitt.vortex21.model.StoryResponse
import retrofit2.Response
import javax.inject.Inject

class StoryRepository @Inject constructor(private val storyApiService: StoryApiService) {
    suspend fun fetchStoriesOfCategory(category: String): Response<StoryResponse> {
        return storyApiService.getStoryByCategory(category)
    }
}