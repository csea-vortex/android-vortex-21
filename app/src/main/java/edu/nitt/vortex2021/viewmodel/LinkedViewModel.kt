package edu.nitt.vortex2021.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.*
import edu.nitt.vortex2021.repository.LinkedRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LinkedViewModel @Inject constructor(
    private val repository: LinkedRepository
) : ViewModel() {
    val latestLinkedQuestionResponse = MutableLiveData<Resource<LatestLinkedQuestion>>()
    val checkedLinkedAnswerResponse = MutableLiveData<Resource<CheckedLinkedAnswer>>()
    val additionalHintResponse = MutableLiveData<Resource<Hint>>()
    val currentScoreRankResponse = MutableLiveData<Resource<CurrentScoreRank>>()

    fun sendLatestLinkedQuestionRequest() {
        viewModelScope.launch {
            latestLinkedQuestionResponse.postValue(Resource.Loading())
            try {
                val response = repository.getLatestLinkedQuestion()
                latestLinkedQuestionResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                Log.i("LinkedViewModel", "current question exception: ${e.message}")
                latestLinkedQuestionResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    fun checkLatestLinkedAnswer(checkLinkedAnswerRequest: CheckLinkedAnswerRequest) {
        viewModelScope.launch {
            checkedLinkedAnswerResponse.postValue(Resource.Loading())
            try {
                val response = repository.checkLatestLinkedAnswer(checkLinkedAnswerRequest)
                checkedLinkedAnswerResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                checkedLinkedAnswerResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    fun getLatestLinkedQuestionAdditionalHint() {
        viewModelScope.launch {
            additionalHintResponse.postValue(Resource.Loading())
            try {
                val response = repository.getLatestLinkedQuestionAdditionalHint()
                additionalHintResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                additionalHintResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    fun getCurrentScoreRank() {
        viewModelScope.launch {
            currentScoreRankResponse.postValue(Resource.Loading())
            try {
                val response = repository.getCurrentScoreRank()
                currentScoreRankResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                currentScoreRankResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

}
