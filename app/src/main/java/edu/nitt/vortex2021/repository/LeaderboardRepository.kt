package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.LeaderboardApiService
import edu.nitt.vortex2021.model.LeaderBoardResponse
import retrofit2.Response
import javax.inject.Inject

class LeaderboardRepository @Inject constructor(private val leaderboardApiService: LeaderboardApiService) {
    suspend fun getLeaderboardRows(eventId: String, starting: Int, ending: Int): Response<LeaderBoardResponse> {
        return leaderboardApiService.getLeaderboardData(eventId, starting, ending)
    }
}