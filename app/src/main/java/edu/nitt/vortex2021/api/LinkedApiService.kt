package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LinkedApiService {
    @GET("events/linked/current-question")
    suspend fun getLatestLinkedQuestion(): Response<LatestLinkedQuestion>

    @GET("events/linked/current-score-rank")
    suspend fun getCurrentScoreRank(): Response<CurrentScoreRank>

    @POST("events/linked/test-latest-linked-answer")
    suspend fun checkLatestLinkedAnswer(@Body checkLinkedAnswerRequest: CheckLinkedAnswerRequest): Response<CheckedLinkedAnswer>

    @GET("events/linked/additional-hint")
    suspend fun getLatestLinkedQuestionAdditionalHint(): Response<Hint>
}