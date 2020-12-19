package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<Story>
)