package edu.nitt.vortex2021.model

data class Question(
        val roundNumber: Int,
        val questionNumber: Int,
        val hints: MutableList<Hint>,
        val additionalHint: Hint,
        val answer: String,
        var isGivenAddHint: Boolean
)
