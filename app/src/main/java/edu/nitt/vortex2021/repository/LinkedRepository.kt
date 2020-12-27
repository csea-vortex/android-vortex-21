package edu.nitt.vortex2021.repository

import edu.nitt.vortex2021.api.LinkedApiService
import edu.nitt.vortex2021.model.*
import retrofit2.Response
import javax.inject.Inject

class LinkedRepository @Inject constructor(private val linkedApiService: LinkedApiService) {
    suspend fun getLatestLinkedQuestion(): Response<LatestLinkedQuestion> {
        return linkedApiService.getLatestLinkedQuestion()
    }

    suspend fun checkLatestLinkedAnswer(checkLinkedAnswerRequest: CheckLinkedAnswerRequest): Response<CheckedLinkedAnswer> {
        return linkedApiService.checkLatestLinkedAnswer(checkLinkedAnswerRequest)
    }

    suspend fun getLatestLinkedQuestionAdditionalHint(): Response<Hint> {
        return linkedApiService.getLatestLinkedQuestionAdditionalHint()
    }

    suspend fun getCurrentScoreRank(): Response<CurrentScoreRank> {
        return linkedApiService.getCurrentScoreRank()
    }
}
