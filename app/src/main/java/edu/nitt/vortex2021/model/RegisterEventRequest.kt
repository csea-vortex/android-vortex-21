package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

class RegisterEventRequest(
    @SerializedName("id") val eventId: String
)