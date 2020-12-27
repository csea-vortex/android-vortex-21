package edu.nitt.vortex2021.helpers

import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Response

fun <T> ViewModel.handleResponse(response: Response<T>): Resource<T> {
    if (response.isSuccessful) {
        response.body()?.let { body ->
            return Resource.Success(body)
        }
    }
    val jsonObject = JSONObject(response.errorBody()!!.string())
    val message = jsonObject.getString("message")
    return Resource.Error(message)
}

