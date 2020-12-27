package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LinkedApiService {
    @GET("event/linked/current-question")
    suspend fun getLatestLinkedQuestion(): Response<LatestLinkedQuestion>

    @GET("event/linked/current-score-rank")
    suspend fun getCurrentScoreRank(): Response<CurrentScoreRank>

    @POST("event/linked/test-latest-linked-answer")
    suspend fun checkLatestLinkedAnswer(@Body checkLinkedAnswerRequest: CheckLinkedAnswerRequest): Response<CheckedLinkedAnswer>

    @GET("event/linked/additional-hint")
    suspend fun getLatestLinkedQuestionAdditionalHint(): Response<Hint>
}
