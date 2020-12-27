package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class LeaderBoardResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("totalUsers")
    val totalUsers: Int,

    @SerializedName("data")
    val data: List<LeaderboardRow>
)