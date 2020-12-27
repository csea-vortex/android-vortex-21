package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.StoryResponse
import edu.nitt.vortex2021.repository.StoryRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    private val repository: StoryRepository
) : ViewModel() {
    val storyResponse = MutableLiveData<Resource<StoryResponse>>()

    fun fetchStoriesOfCategory(category: String) {
        viewModelScope.launch {
            storyResponse.postValue(Resource.Loading())
            try {
                val response = repository.fetchStoriesOfCategory(category)
                storyResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                storyResponse.postValue(Resource.Error("No Internet"))
            }

        }
    }
}