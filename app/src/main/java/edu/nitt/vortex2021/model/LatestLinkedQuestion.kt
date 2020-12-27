package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

//If answered all is true, then all other fields below are null
data class LatestLinkedQuestion(
    val success: Boolean = false,
    val answeredAll: Boolean = false,
    val title: String?,
    val hints: ArrayList<Hint>?,
    var isAdditionalHintTaken: Boolean?,
    var additionalHint: Hint?,
    val level: Int?, // Round Number
    val totalScore: Int?,
    @SerializedName("Qno")
    val qno: Int?, // Question Number
    val marks: Int?,
    val prevAnswers: ArrayList<String>? // List of answers of previous questions
)
