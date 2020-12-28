package edu.nitt.vortex2021.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("isRegistered") val isRegistered: Boolean,
    @SerializedName("eventData") val eventData: EventData
) : Parcelable

@Parcelize
class Events : ArrayList<Event>(), Parcelable