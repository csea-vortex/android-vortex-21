package edu.nitt.vortex2021.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkshopResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val workshops: List<Workshop>
) : Parcelable

