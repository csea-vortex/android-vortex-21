package edu.nitt.vortex2021.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.handleResponse
import edu.nitt.vortex2021.model.LeaderBoardResponse
import edu.nitt.vortex2021.repository.LeaderboardRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class LeaderboardViewModel @Inject constructor(
    private val repository: LeaderboardRepository
) : ViewModel() {
    val leaderboardRowsResponse = MutableLiveData<Resource<LeaderBoardResponse>>()

    /**
     * pageIndex {Int} - starts at 0
     *  Calculates the value of starting and ending index (starts at 1)
     *  for the leaderboard
     */
    fun fetchLeaderboardRowsOf(pageIndex: Int, eventName: String) {
        val starting = pageIndex * Constants.LEADERBOARD_PAGE_SIZE + 1
        val ending = starting + Constants.LEADERBOARD_PAGE_SIZE

        // Note: Hacky-fix cuz event id to-be mentioned in the event route
        //  is not very generic or obtained from any other route
        //  hence have to depend upon if-else

        val eventId: String;
        if (eventName.toLowerCase(Locale.getDefault()).contains("linked")) {
            eventId = "linked"
        } else {
            throw Exception("Invalid eventName")
        }

        viewModelScope.launch {
            leaderboardRowsResponse.postValue(Resource.Loading())
            try {
                val response = repository.getLeaderboardRows(eventId, starting, ending)
                leaderboardRowsResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                leaderboardRowsResponse.postValue(Resource.Error("No internet"))
            }
        }
    }
}