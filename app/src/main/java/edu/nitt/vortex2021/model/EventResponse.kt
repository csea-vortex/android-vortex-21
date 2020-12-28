package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class EventResponse (
    @SerializedName("success") val success : Boolean,
    @SerializedName("data") val data : EventListData
)
