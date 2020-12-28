package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class EventListData (
    @SerializedName("eventList") val events: List<Event>,
    @SerializedName("isUserLoggedIn") val isUserLoggedIn : Boolean,
    @SerializedName("listLength") val listLength : Int
)