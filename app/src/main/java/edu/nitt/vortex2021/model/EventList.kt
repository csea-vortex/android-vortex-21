package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class EventList (
    @SerializedName("isRegistered") val isRegistered : Boolean,
    @SerializedName("eventData") val eventData : EventData
)