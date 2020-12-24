package edu.nitt.vortex2021.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.model.*
import edu.nitt.vortex2021.repository.LinkedRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class LinkedViewModel @Inject constructor(
        private val repository: LinkedRepository
): ViewModel() {
    val latestLinkedQuestionResponse = MutableLiveData<Resource<LatestLinkedQuestion>>()
    val checkedLinkedAnswerResponse = MutableLiveData<Resource<CheckedLinkedAnswer>>()
    val additionalHintResponse = MutableLiveData<Resource<Hint>>()
    val currentScoreRankResponse = MutableLiveData<Resource<CurrentScoreRank>>()

    fun sendLatestLinkedQuestionRequest() {
        viewModelScope.launch {
            latestLinkedQuestionResponse.postValue(Resource.Loading())
            try {
                val response = repository.getLatestLinkedQuestion()
                latestLinkedQuestionResponse.postValue(handleLatestLinkedQuestionResponse(response))
            } catch (e: Exception) {
                Log.i("LinkedViewModel", "exception: ${e.message}")
                latestLinkedQuestionResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    private fun handleLatestLinkedQuestionResponse(response: Response<LatestLinkedQuestion>)
    : Resource<LatestLinkedQuestion> {
        if (response.isSuccessful) {
            response.body()?.let { latestLinkedQuestion ->
                return Resource.Success(latestLinkedQuestion)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
    }

    fun checkLatestLinkedAnswer(checkLinkedAnswerRequest: CheckLinkedAnswerRequest){
        viewModelScope.launch {
            checkedLinkedAnswerResponse.postValue(Resource.Loading())
            try {
                val response = repository.checkLatestLinkedAnswer(checkLinkedAnswerRequest)
                checkedLinkedAnswerResponse.postValue(handleCheckedLinkedAnswerResponse(response))
            } catch (e: Exception) {
                checkedLinkedAnswerResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    private fun handleCheckedLinkedAnswerResponse(response: Response<CheckedLinkedAnswer>)
            : Resource<CheckedLinkedAnswer> {
        if (response.isSuccessful) {
            response.body()?.let { checkedLinkedAnswer ->
                return Resource.Success(checkedLinkedAnswer)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
    }

    fun getLatestLinkedQuestionAdditionalHint() {
        viewModelScope.launch {
            additionalHintResponse.postValue(Resource.Loading())
            try {
                val response = repository.getLatestLinkedQuestionAdditionalHint()
                additionalHintResponse.postValue(handleAdditionalHintResponse(response))
            } catch (e: Exception) {
                additionalHintResponse.postValue(Resource.Error("No internet"))
            }
        }
    }

    private fun handleAdditionalHintResponse(response: Response<Hint>)
            : Resource<Hint> {
        if (response.isSuccessful) {
            response.body()?.let { additionalHint ->
                return Resource.Success(additionalHint)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
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

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                return Resource.Success(body)
            }
        }
        val jsonObject = JSONObject(response.errorBody()!!.string())
        val message = jsonObject.getString("message")
        return Resource.Error(message)
    }
}