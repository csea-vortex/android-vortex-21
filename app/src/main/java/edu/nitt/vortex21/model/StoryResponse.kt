package edu.nitt.vortex21.model

data class StoryResponse(
    val success: Boolean,
    val data: List<Story>
)