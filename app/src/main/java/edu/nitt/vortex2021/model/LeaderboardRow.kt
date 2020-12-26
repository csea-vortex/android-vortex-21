package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

class LeaderboardRow(
    @SerializedName("sno")
    val rank: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("totalScore")
    val score: Int,

    @SerializedName("college")
    val college: String,
)