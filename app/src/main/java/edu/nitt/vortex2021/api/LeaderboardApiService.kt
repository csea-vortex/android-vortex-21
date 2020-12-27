package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.LeaderBoardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Probably in future as we get more events as
 * each event will have a similar route but different
 * leaderboard -- keeping this implementation separate.
 */
interface LeaderboardApiService {
    @GET("events/{eventId}/leaderboard")
    suspend fun getLeaderboardData(
        @Path("eventId") eventId: String,
        @Query("starting") starting: Int,
        @Query("ending") ending: Int
    ): Response<LeaderBoardResponse>
}