package edu.nitt.vortex21.repository

import edu.nitt.vortex21.api.VortexApi
import edu.nitt.vortex21.model.StoryResponse
import retrofit2.Response

class StoryRepository {
    suspend fun fetchStoriesOfCategory(category: String): Response<StoryResponse> {
        return VortexApi.storyApiService.getStoryByCategory(category)
    }
}