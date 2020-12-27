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
) {
    override operator fun equals(other: Any?): Boolean {
        return other != null
                && other is LeaderboardRow
                && rank == other.rank
                && username == other.username
                && score == other.score
                && college == other.college
    }
}