package edu.nitt.vortex2021.model

import java.util.*

data class CheckedLinkedAnswer(
        val isCorrectSolution: Boolean,
        val marksAwarded: Int?,
        val totalScore: Int?,
        val latestTime: Date? //DateTime
)
