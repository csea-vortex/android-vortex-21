package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.CollegeResponse
import edu.nitt.vortex2021.repository.DataRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DataViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    val collegesResponse = MutableLiveData<Resource<CollegeResponse>>()

    fun fetchCollegeList() {
        viewModelScope.launch {
            collegesResponse.postValue((Resource.Loading()))
            try {
                val response = repository.fetchColleges()
                collegesResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                collegesResponse.postValue(Resource.Error("No Internet"))
            }
        }
    }
}
