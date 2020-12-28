package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class CurrentScoreRank(
    @SerializedName("totalScore") val totalScore: Int,
    @SerializedName("rank") val rank: Int
)